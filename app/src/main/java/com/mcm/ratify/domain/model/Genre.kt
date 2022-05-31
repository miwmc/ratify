package com.mcm.ratify.domain.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val genre_id: Int = 0,
    val name: String = "",
    val picture: String = "",
)