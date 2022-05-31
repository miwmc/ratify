package com.mcm.ratify.ui.track_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.mcm.ratify.domain.model.Track
import com.mcm.ratify.domain.use_case.GetAuthInstanceUseCase
import com.mcm.ratify.domain.use_case.GetTrackDetailUseCase
import com.mcm.ratify.util.ERROR_MESSAGE
import com.mcm.ratify.util.PARAM_TRACK_ID
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TrackViewModel @Inject constructor(
    private val getTrackDetailUseCase: GetTrackDetailUseCase,
    val getAuthInstanceUseCase: GetAuthInstanceUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(TrackDetailState())
    val state: State<TrackDetailState> = _state

    init {
        savedStateHandle.get<String>(PARAM_TRACK_ID)?.let { trackId ->
            getTrackDetail(trackId.toLong())
        }
    }

    fun getTrackDetail(trackId: Long) {
        getTrackDetailUseCase(trackId).onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = TrackDetailState(track = result.data ?: Track())
                }
                is Resource.Error -> {
                    _state.value = TrackDetailState(error = result.message ?: ERROR_MESSAGE)
                }
                is Resource.Loading -> {
                    _state.value = TrackDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}