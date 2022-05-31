package com.mcm.ratify.domain.model

data class Track(
    val album_id: Long = 0L,
    val cover: String = "",
    val rank: Long = 0L,
    var rating: Float = 0.0f,
    val track_id: Long = 0L,
    val title: String = "",
    val track_position: Int = 0,
    var artist_name: String = "",
    var num_reviews: Int = 0,
    var isReviewedByUser: Boolean = false
)