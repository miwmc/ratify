package com.mcm.ratify.data.remote.dto

import com.mcm.ratify.domain.model.Artist

data class ArtistDto(
    val id: Long,
    val link: String,
    val name: String,
    val picture: String,
    val picture_big: String,
    val picture_medium: String,
    val picture_small: String,
    val picture_xl: String,
    val radio: Boolean,
    val tracklist: String,
)

fun ArtistDto.toArtist(): Artist {
    return Artist(
        artist_id = id,
        name = name,
        picture = picture
    )
}