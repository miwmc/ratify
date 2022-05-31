package com.mcm.ratify.ui.track_detail

import com.mcm.ratify.domain.model.Track

data class TrackDetailState(
    val isLoading: Boolean = false,
    val track: Track? = null,
    val error: String = ""
)
