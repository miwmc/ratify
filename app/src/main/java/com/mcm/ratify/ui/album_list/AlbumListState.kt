package com.mcm.ratify.ui.album_list

import com.mcm.ratify.domain.model.Album

data class AlbumListState(
    val isLoading: Boolean = false,
    val albums: List<Album> = emptyList(),
    val error: String = ""
)
