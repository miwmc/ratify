package com.mcm.ratify.domain.use_case

import com.mcm.ratify.domain.repository.RatifyRepository
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTrackReviewsCountUseCase @Inject constructor(
    private val repository: RatifyRepository
) {
    operator fun invoke(
        trackId: Long
    ): Flow<Resource<Int>> = flow {
        emit(Resource.Loading(0))
        val count = repository.getTrackReviewsCount(trackId)
        emit(count)
    }
}