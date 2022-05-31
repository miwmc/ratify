package com.mcm.ratify.ui.review_list

import com.mcm.ratify.domain.model.Review

data class ReviewListState(
    val isLoading: Boolean = false,
    val list: List<Review>? = null,
    val error: String = ""
)
