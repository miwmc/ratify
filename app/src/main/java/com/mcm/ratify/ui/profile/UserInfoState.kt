package com.mcm.ratify.ui.profile

import com.mcm.ratify.domain.model.User

data class UserInfoState(
    val isLoading: Boolean = false,
    val info: User? = null,
    val error: String = ""
)
