package com.mcm.ratify.domain.use_case

import com.mcm.ratify.domain.model.Track
import com.mcm.ratify.domain.repository.RatifyRepository
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTrackDetailUseCase @Inject constructor(
    private val repository: RatifyRepository
) {
    operator fun invoke(
        trackId: Long
    ): Flow<Resource<Track>> = flow {
        emit(Resource.Loading(Track()))
        val track = repository.getTrackDetails(trackId)
        if (track is Resource.Success) {
            track.data!!.num_reviews = repository.getTrackReviewsCount(trackId).data!!
            val artistId = repository.getArtistFromAlbum(track.data.album_id).data!!
            track.data.artist_name = repository.getArtistDetails(artistId).data!!.name
            track.data.rating = repository.getTrackRating(trackId).data!!
            track.data.isReviewedByUser = repository.isTrackReviewedByUser(trackId).data!!
        }
        emit(track)
    }
}