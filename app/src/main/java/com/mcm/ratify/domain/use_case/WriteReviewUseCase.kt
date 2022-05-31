package com.mcm.ratify.domain.use_case

import com.mcm.ratify.domain.repository.RatifyRepository
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WriteReviewUseCase @Inject constructor(
    private val repository: RatifyRepository
) {
    operator fun invoke(
        trackId: Long,
        rating: Int,
        content: String
    ): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        val review = repository.writeReview(trackId, rating, content)
        emit(review)
    }
}