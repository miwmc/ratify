package com.mcm.ratify.data.remote.dto

import com.mcm.ratify.domain.model.Track

data class TrackDto(
    val disk_number: Int,
    val duration: Int,
    val explicit_content_cover: Int,
    val explicit_content_lyrics: Int,
    val explicit_lyrics: Boolean,
    val id: Long,
    val isrc: String,
    val link: String,
    val md5_image: String,
    val preview: String,
    val rank: Long,
    val readable: Boolean,
    val title: String,
    val title_short: String,
    val title_version: String,
    val track_position: Int,
    val type: String
)

fun TrackDto.toTrack(albumId: Long, cover: String): Track {
    return Track(
        track_id = id,
        title = title,
        track_position = track_position,
        album_id = albumId,
        rank = rank,
        cover = cover
    )
}