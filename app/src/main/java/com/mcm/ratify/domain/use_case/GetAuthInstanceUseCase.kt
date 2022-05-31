package com.mcm.ratify.domain.use_case

import com.google.firebase.auth.FirebaseAuth
import com.mcm.ratify.domain.repository.RatifyRepository
import javax.inject.Inject

class GetAuthInstanceUseCase @Inject constructor(
    private val repository: RatifyRepository
) {
    operator fun invoke(): FirebaseAuth = repository.getAuthInstance()
}