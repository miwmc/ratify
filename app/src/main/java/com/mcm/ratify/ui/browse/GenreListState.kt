package com.mcm.ratify.ui.browse

import com.mcm.ratify.domain.model.Genre

data class GenreListState(
    val isLoading: Boolean = false,
    val genres: List<Genre> = emptyList(),
    val error: String = ""
)
