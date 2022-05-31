package com.mcm.ratify.domain.model

import com.google.firebase.Timestamp

data class Review(
    val review_id: String = "",
    val rating: Int = 0,
    val content: String = "",
    val date: Timestamp = Timestamp.now(),
    val track_id: Long = 0L,
    val user_id: String = "",
    var username: String = "",
    var user_photo: String = "",
    var track_title: String = "",
    var artist_name: String = "",
)