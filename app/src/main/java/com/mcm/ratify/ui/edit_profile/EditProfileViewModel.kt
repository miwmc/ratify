package com.mcm.ratify.ui.edit_profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.mcm.ratify.domain.use_case.EditUserProfileUseCase
import com.mcm.ratify.ui.login.FirebaseUserState
import com.mcm.ratify.util.ERROR_MESSAGE
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val editUserProfileUseCase: EditUserProfileUseCase
) : ViewModel() {

    private val _state = mutableStateOf(FirebaseUserState())
    val state: State<FirebaseUserState> = _state

    fun editUserProfile(username: String, email: String, password: String) {
        editUserProfileUseCase(username, email, password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = FirebaseUserState(user = result.data)
                }
                is Resource.Error -> {
                    _state.value = FirebaseUserState(error = result.message ?: ERROR_MESSAGE)
                }
                is Resource.Loading -> {
                    _state.value = FirebaseUserState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}