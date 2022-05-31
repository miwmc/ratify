package com.mcm.ratify.domain.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mcm.ratify.data.remote.dto.Chart
import com.mcm.ratify.data.remote.dto.GenreList
import com.mcm.ratify.data.remote.dto.TrackList
import com.mcm.ratify.domain.model.*
import com.mcm.ratify.util.PAGE_SIZE
import com.mcm.ratify.util.Resource

interface RatifyRepository {

    fun getAuthInstance(): FirebaseAuth

    suspend fun getGenreList(): Resource<List<Genre>>

    suspend fun getAlbumListByGenre(
        genreId: Int
    ): Resource<List<Album>>

    suspend fun getTrackListByAlbum(
        albumId: Long
    ): Resource<List<Track>>

    suspend fun getTrackDetails(
        trackId: Long
    ): Resource<Track>

    suspend fun getTrackReviews(
        trackId: Long
    ): Resource<List<Review>>

    suspend fun getTrackReviewsCount(
        trackId: Long
    ): Resource<Int>

    suspend fun getTrackRating(
        trackId: Long
    ): Resource<Float>

    suspend fun isTrackReviewedByUser(
        trackId: Long
    ): Resource<Boolean>

    suspend fun signUpUser(
        username: String,
        email: String,
        password: String
    ): Resource<FirebaseUser?>

    suspend fun createUserAdditionalInfo(
        user: FirebaseUser,
        username: String = ""
    ): Resource<Boolean>

    suspend fun loginUser(
        email: String,
        password: String
    ): Resource<FirebaseUser?>

    suspend fun signInWithGoogle(
        googleSignInAccount: GoogleSignInAccount
    ): Resource<FirebaseUser?>

    suspend fun signInAnonymously(): Resource<FirebaseUser?>

    suspend fun getUserInfo(
        userId: String
    ): Resource<User>

    suspend fun editUserProfile(
        username: String,
        email: String,
        password: String
    ): Resource<FirebaseUser>

    suspend fun getUserTracksRated(
        userId: String
    ): Resource<Int>

    suspend fun getArtistFromAlbum(
        albumId: Long
    ): Resource<Long>

    suspend fun getArtistDetails(
        artistId: Long
    ): Resource<Artist>

    suspend fun writeReview(
        trackId: Long,
        rating: Int,
        content: String
    ): Resource<Boolean>

    suspend fun getHomeReviews(): Resource<List<Review>>

    suspend fun getRemoteGenreList() : Resource<GenreList>

    suspend fun getAlbumList(
        id: Int,
        index: Int = 0,
        limit: Int = PAGE_SIZE
    ) : Resource<Chart>

    suspend fun getTrackList(
        id: Long,
        index: Int = 0,
        limit: Int = PAGE_SIZE
    ) : Resource<TrackList>

    suspend fun writeGenreToFirestore(genre: Genre): Resource<Boolean>

    suspend fun writeAlbumToFirestore(album: Album): Resource<Boolean>

    suspend fun writeArtistToFirestore(artist: Artist): Resource<Boolean>

    suspend fun writeTrackToFirestore(track: Track): Resource<Boolean>
}