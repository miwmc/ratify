package com.mcm.ratify.domain.model

data class Album(
    val album_id: Long = 0L,
    val title: String = "",
    val artist_id: Long = 0L,
    val position: Int = 0,
    val cover: String = "",
    val genre_id: Int = 0,
)
