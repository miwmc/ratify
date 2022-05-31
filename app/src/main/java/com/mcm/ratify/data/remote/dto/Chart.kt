package com.mcm.ratify.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Chart(
    @SerializedName("albums")
    val albumList: AlbumList
)