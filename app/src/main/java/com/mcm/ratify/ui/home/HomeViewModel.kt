package com.mcm.ratify.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.mcm.ratify.domain.use_case.GetHomeReviewsListUseCase
import com.mcm.ratify.ui.review_list.ReviewListState
import com.mcm.ratify.util.ERROR_MESSAGE
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeReviewsListUseCase: GetHomeReviewsListUseCase
): ViewModel() {

    private val _state = mutableStateOf(ReviewListState())
    val state: State<ReviewListState> = _state

    init {
        getHomeReviews()
    }

    fun getHomeReviews() {
        getHomeReviewsListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ReviewListState(list = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = ReviewListState(error = result.message ?: ERROR_MESSAGE)
                }
                is Resource.Loading -> {
                    _state.value = ReviewListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}