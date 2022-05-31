package com.mcm.ratify.ui.album_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.mcm.ratify.domain.use_case.GetAlbumListUseCase
import com.mcm.ratify.util.ERROR_MESSAGE
import com.mcm.ratify.util.PARAM_GENRE_ID
import com.mcm.ratify.util.PARAM_GENRE_NAME
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val getAlbumListUseCase: GetAlbumListUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(AlbumListState())
    val state: State<AlbumListState> = _state

    var genreName: String = ""

    init {
        savedStateHandle.get<String>(PARAM_GENRE_ID)?.let { genreId ->
            getAlbumList(genreId.toInt())
        }
        savedStateHandle.get<String>(PARAM_GENRE_NAME)?.let { genreName ->
            this.genreName = genreName
        }
    }

    fun getAlbumList(genreId: Int) {
        getAlbumListUseCase(genreId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = AlbumListState(albums = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = AlbumListState(error = result.message ?: ERROR_MESSAGE)
                }
                is Resource.Loading -> {
                    _state.value = AlbumListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

//    private var currentPage = 0
//
//    val albumListStateFlow =
//        MutableStateFlow<Resource<MutableList<Album>>>(Resource.Loading(mutableListOf()))
//    val endReached = mutableStateOf(false)
//
//    fun loadAlbumList(genreId: Int) {
//        viewModelScope.launch {
//            repository.getAlbumListByGenre(
//                genreId,
//                currentPage * PAGE_SIZE,
//                PAGE_SIZE.toLong()
//            ).collect { result ->
//                albumListStateFlow.value = result
//                if (result is Resource.Success) {
//                    Log.d(TAG, result.data.toString())
//                    endReached.value = currentPage * PAGE_SIZE >= result.data!!.size - 1
//                    Log.d(TAG, "$currentPage * $PAGE_SIZE >= ${result.data.size - 1}")
//                    currentPage++
//                }
//            }
//        }
//    }
//
//    companion object {
//        private const val TAG = "GenreViewModel"
//    }
}