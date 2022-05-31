package com.mcm.ratify.ui.login

import com.google.firebase.auth.FirebaseUser

data class FirebaseUserState(
    val isLoading: Boolean = false,
    val user: FirebaseUser? = null,
    val error: String = ""
)
