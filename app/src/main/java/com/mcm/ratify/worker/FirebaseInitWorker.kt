package com.mcm.ratify.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import com.mcm.ratify.data.remote.dto.toAlbum
import com.mcm.ratify.data.remote.dto.toArtist
import com.mcm.ratify.data.remote.dto.toTrack
import com.mcm.ratify.domain.repository.RatifyRepository
import kotlinx.coroutines.coroutineScope

@HiltWorker
class FirebaseInitWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: RatifyRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        Log.d(TAG, "Worker Started")
        try {
            val genreList = repository.getRemoteGenreList().data!!.data
            genreList.forEach { genre ->
                repository.writeGenreToFirestore(genre)
                val albumList = repository.getAlbumList(genre.genre_id).data!!.albumList.data
                albumList.forEach { albumDto ->
                    val album = albumDto.toAlbum(genre.genre_id)
                    repository.writeAlbumToFirestore(album)
                    val artistDto = albumDto.artist
                    val artist = artistDto.toArtist()
                    repository.writeArtistToFirestore(artist)
                    val trackList = repository.getTrackList(albumDto.id).data!!.data
                    trackList.forEach { trackDto ->
                        val track = trackDto.toTrack(album.album_id, album.cover)
                        repository.writeTrackToFirestore(track)
                    }
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "FirebaseInitWorker"
        const val WORK_NAME = "com.mcm.ratify.worker.FirebaseInitWorker"
    }
}