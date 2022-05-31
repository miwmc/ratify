package com.mcm.ratify.ui.track_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.mcm.ratify.domain.use_case.GetTrackListUseCase
import com.mcm.ratify.util.ERROR_MESSAGE
import com.mcm.ratify.util.PARAM_ALBUM_ID
import com.mcm.ratify.util.PARAM_ALBUM_NAME
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val getTrackListUseCase: GetTrackListUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(TrackListState())
    val state: State<TrackListState> = _state

    var albumName: String = ""

    init {
        savedStateHandle.get<String>(PARAM_ALBUM_ID)?.let { albumId ->
            getTrackList(albumId.toLong())
        }
        savedStateHandle.get<String>(PARAM_ALBUM_NAME)?.let { albumName ->
            this.albumName = albumName
        }
    }

    fun getTrackList(albumId: Long) {
        getTrackListUseCase(albumId).onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = TrackListState(tracks = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = TrackListState(error = result.message ?: ERROR_MESSAGE)
                }
                is Resource.Loading -> {
                    _state.value = TrackListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}