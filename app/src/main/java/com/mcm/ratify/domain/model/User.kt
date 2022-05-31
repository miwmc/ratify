package com.mcm.ratify.domain.model

import com.google.firebase.Timestamp

data class User(
    val user_id: String = "",
    val username: String = "",
    val type: Int = 0,
    val photo_url: String = "",
    val date_joined: Timestamp = Timestamp.now(),
    val is_anonymous: Boolean = false,
    var tracks_reviewed: Int = 0
)
