package com.mcm.ratify.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.scopes.ActivityScoped
import com.mcm.ratify.data.remote.dto.Chart
import com.mcm.ratify.data.remote.dto.GenreList
import com.mcm.ratify.data.remote.dto.TrackList
import com.mcm.ratify.domain.model.*
import com.mcm.ratify.domain.repository.RatifyRepository
import com.mcm.ratify.util.ERROR_MESSAGE
import com.mcm.ratify.util.FREE_TYPE
import com.mcm.ratify.util.GUEST_TYPE
import com.mcm.ratify.util.UI_AVATARS_BASE_URL
import com.mcm.ratify.remote.retrofit.services.DeezerService
import com.mcm.ratify.util.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ActivityScoped
class RatifyRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val deezerService: DeezerService
) : RatifyRepository {

    // TODO: Firebase methods and redo repository

    override fun getAuthInstance(): FirebaseAuth {
        return auth
    }

    override suspend fun getGenreList(): Resource<List<Genre>> {
        try {
            firestore.collection("genres")
                .orderBy("genre_id")
                .get()
                .await()
                .run {
                    return Resource.Success(toObjects(Genre::class.java))
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun getAlbumListByGenre(
        genreId: Int
    ): Resource<List<Album>> {
        try {
            firestore.collection("albums")
                .whereEqualTo("genre_id", genreId)
                .orderBy("position")
                .get()
                .await()
                .run {
                    return Resource.Success(toObjects(Album::class.java))
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun getTrackListByAlbum(
        albumId: Long
    ): Resource<List<Track>> {
        try {
            firestore.collection("tracks")
                .whereEqualTo("album_id", albumId)
                .orderBy("track_position")
                .get()
                .await()
                .run {
                    return Resource.Success(toObjects(Track::class.java))
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun getTrackDetails(
        trackId: Long
    ): Resource<Track> {
        try {
            firestore.collection("tracks")
                .document(trackId.toString())
                .get()
                .await()
                .run {
                    val track = toObject(Track::class.java)!!
                    return Resource.Success(track)
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun getTrackReviews(
        trackId: Long
    ): Resource<List<Review>> {
        try {
            firestore.collection("reviews")
                .whereEqualTo("track_id", trackId)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()
                .run {
                    val list = toObjects(Review::class.java)
                    return Resource.Success(list)
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun getTrackReviewsCount(
        trackId: Long
    ): Resource<Int> {
        return try {
            Resource.Success(getTrackReviews(trackId).data!!.size)
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun getTrackRating(trackId: Long): Resource<Float> {
        try {
            var sum = 0.0f
            val reviews = getTrackReviews(trackId).data!!
            if (reviews.isEmpty()) return Resource.Success(0.0f)
            reviews.forEach {
                sum += it.rating
            }
            return Resource.Success(String.format("%.1f", sum / reviews.size).toFloat())
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun isTrackReviewedByUser(
        trackId: Long
    ): Resource<Boolean> {
        try {
            auth.currentUser?.let {
                firestore.collection("reviews")
                    .document(it.uid + "-" + trackId.toString())
                    .get()
                    .await()
                    .run {
                        return Resource.Success(exists())
                    }
            }
            return Resource.Error(message = ERROR_MESSAGE)
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }


    override suspend fun signUpUser(
        username: String,
        email: String,
        password: String
    ): Resource<FirebaseUser?> {
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .await()
                .run {
                    user?.let { createUserAdditionalInfo(it, username) }
                    return Resource.Success(user)
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun createUserAdditionalInfo(
        user: FirebaseUser,
        username: String
    ): Resource<Boolean> {
        try {
            val photoUrl =
                if (user.photoUrl != null) user.photoUrl
                else "$UI_AVATARS_BASE_URL=${
                    if (user.isAnonymous) user.uid else username
                }"
            val userInfo = hashMapOf(
                "user_id" to user.uid,
                "username" to if (user.isAnonymous) user.uid else username,
                "type" to if (user.isAnonymous) GUEST_TYPE else FREE_TYPE,
                "photo_url" to photoUrl.toString(),
                "date_joined" to FieldValue.serverTimestamp(),
                "is_anonymous" to user.isAnonymous
            )
            firestore.collection("users")
                .document(user.uid)
                .set(userInfo)
                .await()
                .run {
                    return Resource.Success(true)
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): Resource<FirebaseUser?> {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .await()
                .run {
                    return Resource.Success(user)
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun signInWithGoogle(googleSignInAccount: GoogleSignInAccount): Resource<FirebaseUser?> {
        try {
            val credential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
            auth.signInWithCredential(credential)
                .await()
                .run {
                    user?.let {
                        user!!.displayName?.let { it1 ->
                            createUserAdditionalInfo(
                                it,
                                it1
                            )
                        }
                    }
                    return Resource.Success(user)
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun signInAnonymously(): Resource<FirebaseUser?> {
        try {
            auth.signInAnonymously()
                .await()
                .run {
                    user?.let { createUserAdditionalInfo(it) }
                    return Resource.Success(user)
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun getUserInfo(userId: String): Resource<User> {
        try {
            firestore.collection("users")
                .document(userId)
                .get()
                .await()
                .run {
                    val user = toObject(User::class.java)!!
                    return Resource.Success(user)
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun editUserProfile(
        username: String,
        email: String,
        password: String
    ): Resource<FirebaseUser> {
        try {
            auth.currentUser?.let { user ->
                if (username.isNotBlank()) {
                    firestore.collection("users")
                        .document(user.uid)
                        .update("username", username)
                        .await()
                }
                if (email.isNotBlank()) {
                    user.updateEmail(email).await()
                }
                if (password.isNotBlank()) {
                    user.updatePassword(password).await()
                }
                return Resource.Success(user)
            }
            return Resource.Error(message = ERROR_MESSAGE)
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun getUserTracksRated(userId: String): Resource<Int> {
        try {
            firestore.collection("reviews")
                .whereEqualTo("user_id", userId)
                .get()
                .await()
                .run {
                    return Resource.Success(this.size())
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun getArtistFromAlbum(albumId: Long): Resource<Long> {
        try {
            firestore.collection("albums")
                .document(albumId.toString())
                .get()
                .await()
                .run {
                    return Resource.Success(toObject(Album::class.java)!!.artist_id)
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun getArtistDetails(artistId: Long): Resource<Artist> {
        try {
            firestore.collection("artists")
                .document(artistId.toString())
                .get()
                .await()
                .run {
                    return Resource.Success(toObject(Artist::class.java)!!)
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun writeReview(
        trackId: Long,
        rating: Int,
        content: String
    ): Resource<Boolean> {
        try {
            auth.currentUser?.let {
                val documentId = it.uid + "-" + trackId.toString()
                val data = hashMapOf(
                    "content" to content,
                    "date" to FieldValue.serverTimestamp(),
                    "rating" to rating,
                    "review_id" to documentId,
                    "track_id" to trackId,
                    "user_id" to it.uid
                )
                firestore.collection("reviews")
                    .document(documentId)
                    .set(data)
                    .await()
                    .run {
                        return Resource.Success(true)
                    }
            }
            return Resource.Error(message = ERROR_MESSAGE)
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun getHomeReviews(): Resource<List<Review>> {
        try {
            firestore.collection("reviews")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()
                .run {
                    val list = toObjects(Review::class.java)
                    return Resource.Success(list)
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun getRemoteGenreList(): Resource<GenreList> {
        return try {
            Resource.Success(deezerService.getGenreList())
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun getAlbumList(id: Int, index: Int, limit: Int): Resource<Chart> {
        return try {
            Resource.Success(deezerService.getAlbumList(id, index, limit))
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun getTrackList(id: Long, index: Int, limit: Int): Resource<TrackList> {
        return try {
            Resource.Success(deezerService.getTrackList(id, index, limit))
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun writeGenreToFirestore(genre: Genre): Resource<Boolean> {
        try {
            val data = hashMapOf(
                "genre_id" to genre.genre_id,
                "name" to genre.name,
                "picture" to genre.picture
            )
            firestore.collection("genres")
                .document(genre.genre_id.toString())
                .set(data)
                .await()
                .run {
                    return Resource.Success(true)
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun writeAlbumToFirestore(album: Album): Resource<Boolean> {
        try {
            val data = hashMapOf(
                "album_id" to album.album_id,
                "artist_id" to album.artist_id,
                "cover" to album.cover,
                "genre_id" to album.genre_id,
                "position" to album.position,
                "title" to album.title
            )
            firestore.collection("albums")
                .document(album.album_id.toString())
                .set(data)
                .await()
                .run {
                    return Resource.Success(true)
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun writeArtistToFirestore(artist: Artist): Resource<Boolean> {
        try {
            val data = hashMapOf(
                "artist_id" to artist.artist_id,
                "name" to artist.name,
                "picture" to artist.picture
            )
            firestore.collection("artists")
                .document(artist.artist_id.toString())
                .set(data)
                .await()
                .run {
                    return Resource.Success(true)
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    override suspend fun writeTrackToFirestore(track: Track): Resource<Boolean> {
        try {
            val data = hashMapOf(
                "album_id" to track.album_id,
                "cover" to track.cover,
                "rank" to track.rank,
                "title" to track.title,
                "track_id" to track.track_id,
                "track_position" to track.track_position
            )
            firestore.collection("tracks")
                .document(track.track_id.toString())
                .set(data)
                .await()
                .run {
                    return Resource.Success(true)
                }
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE)
        }
    }
}