package com.mcm.ratify.ui.write_review

import com.google.firebase.auth.FirebaseUser

data class ReviewState(
    val isLoading: Boolean = false,
    val isDone: Boolean = false,
    val error: String = ""
)
