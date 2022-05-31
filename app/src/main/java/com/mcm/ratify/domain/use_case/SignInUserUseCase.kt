package com.mcm.ratify.domain.use_case

import com.google.firebase.auth.FirebaseUser
import com.mcm.ratify.domain.repository.RatifyRepository
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUserUseCase @Inject constructor(
    private val repository: RatifyRepository
) {
    operator fun invoke(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> = flow {
        emit(Resource.Loading())
        val user = repository.loginUser(email, password)
        emit(user)
    }
}