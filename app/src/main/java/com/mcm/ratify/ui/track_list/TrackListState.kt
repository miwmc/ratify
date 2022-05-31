package com.mcm.ratify.ui.track_list

import com.mcm.ratify.domain.model.Track

data class TrackListState(
    val isLoading: Boolean = false,
    val tracks: List<Track> = emptyList(),
    val error: String = ""
)
