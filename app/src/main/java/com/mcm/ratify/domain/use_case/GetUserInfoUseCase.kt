package com.mcm.ratify.domain.use_case

import com.mcm.ratify.domain.model.User
import com.mcm.ratify.domain.repository.RatifyRepository
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repository: RatifyRepository
) {
    operator fun invoke(
        userId: String
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        val userInfo = repository.getUserInfo(userId)
        if (userInfo is Resource.Success) {
            userInfo.data!!.tracks_reviewed = repository.getUserTracksRated(userId).data!!
        }
        emit(userInfo)
    }
}