package com.mcm.ratify.data.remote.dto

import com.mcm.ratify.domain.model.Album

data class AlbumDto(
    val artist: ArtistDto,
    val cover: String,
    val cover_big: String,
    val cover_medium: String,
    val cover_small: String,
    val cover_xl: String,
    val explicit_lyrics: Boolean,
    val id: Long,
    val link: String,
    val md5_image: String,
    val position: Int,
    val record_type: String,
    val title: String,
    val tracklist: String,
    val type: String
)

fun AlbumDto.toAlbum(genreId: Int): Album {
    return Album(
        album_id = id,
        title = title,
        artist_id = artist.id,
        position = position,
        cover = cover,
        genre_id = genreId
    )
}