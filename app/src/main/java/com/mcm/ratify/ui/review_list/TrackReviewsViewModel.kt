package com.mcm.ratify.ui.review_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.mcm.ratify.domain.use_case.GetTrackReviewsListUseCase
import com.mcm.ratify.util.ERROR_MESSAGE
import com.mcm.ratify.util.PARAM_TRACK_ID
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TrackReviewsViewModel @Inject constructor(
    private val getTrackReviewsListUseCase: GetTrackReviewsListUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(ReviewListState())
    val state: State<ReviewListState> = _state

    init {
        savedStateHandle.get<String>(PARAM_TRACK_ID)?.let { trackId ->
            getTrackReviews(trackId.toLong())
        }
    }

    fun getTrackReviews(trackId: Long) {
        getTrackReviewsListUseCase(trackId).onEach { result ->
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