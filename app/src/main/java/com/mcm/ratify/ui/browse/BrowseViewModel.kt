package com.mcm.ratify.ui.browse

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.mcm.ratify.domain.use_case.GetGenreListUseCase
import com.mcm.ratify.util.ERROR_MESSAGE
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val getGenreListUseCase: GetGenreListUseCase
) : ViewModel() {

    private val _state = mutableStateOf(GenreListState())
    val state: State<GenreListState> = _state

    init {
        getGenreList()
    }

    fun getGenreList() {
        getGenreListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = GenreListState(genres = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = GenreListState(error = result.message ?: ERROR_MESSAGE)
                }
                is Resource.Loading -> {
                    _state.value = GenreListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

//    val genreListStateFlow =
//        MutableStateFlow<Resource<MutableList<Genre>>>(Resource.Loading(mutableListOf()))
//
//    init {
//        loadGenreList()
//    }
//
//    fun loadGenreList() {
//        viewModelScope.launch {
//            repository.getGenreList().collect {
//                genreListStateFlow.value = it
//            }
//        }
//    }
}