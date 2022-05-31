package com.mcm.ratify.domain.use_case

import com.mcm.ratify.domain.model.Review
import com.mcm.ratify.domain.repository.RatifyRepository
import com.mcm.ratify.util.ERROR_MESSAGE
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTrackReviewsListUseCase @Inject constructor(
    private val repository: RatifyRepository
) {
    operator fun invoke(
        trackId: Long
    ): Flow<Resource<List<Review>>> = flow {
        try {
            emit(Resource.Loading())
            val reviewList = repository.getTrackReviews(trackId)
            if (reviewList is Resource.Success) {
                val trackDetails = repository.getTrackDetails(trackId).data
                val artistId = repository.getArtistFromAlbum(trackDetails!!.album_id).data!!
                trackDetails.artist_name = repository.getArtistDetails(artistId).data!!.name
                reviewList.data?.forEach { review ->
                    review.track_title = trackDetails.title
                    review.artist_name = trackDetails.artist_name
                    val userInfo = repository.getUserInfo(review.user_id).data
                    if (userInfo != null) {
                        review.username = userInfo.username
                        review.user_photo = userInfo.photo_url
                    }
                }
            }
            emit(reviewList)
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ERROR_MESSAGE))
        }
    }
}