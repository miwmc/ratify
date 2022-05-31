package com.mcm.ratify.ui.write_review

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.mcm.ratify.domain.use_case.WriteReviewUseCase
import com.mcm.ratify.util.ERROR_MESSAGE
import com.mcm.ratify.util.PARAM_TRACK_ID
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WriteReviewViewModel @Inject constructor(
    private val writeReviewUseCase: WriteReviewUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(ReviewState())
    val state: State<ReviewState> = _state

    var trackId: String? = savedStateHandle.get<String>(PARAM_TRACK_ID)

    fun writeReview(rating: Int, content: String) {
        trackId?.let { trackId ->
            writeReviewUseCase(trackId.toLong(), rating, content).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = ReviewState(isDone = result.data!!)
                    }
                    is Resource.Error -> {
                        _state.value = ReviewState(error = result.message ?: ERROR_MESSAGE)
                    }
                    is Resource.Loading -> {
                        _state.value = ReviewState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }

    }
}