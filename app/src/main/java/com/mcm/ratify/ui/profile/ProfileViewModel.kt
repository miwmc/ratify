package com.mcm.ratify.ui.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import com.mcm.ratify.domain.use_case.GetAuthInstanceUseCase
import com.mcm.ratify.domain.use_case.GetUserInfoUseCase
import com.mcm.ratify.util.ERROR_MESSAGE
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val getAuthInstanceUseCase: GetAuthInstanceUseCase,
    val getUserInfoUseCase: GetUserInfoUseCase
): ViewModel() {

    private val _state = mutableStateOf(UserInfoState())
    val state: State<UserInfoState> = _state

    val currentUser: FirebaseUser?

    init {
        getUserInfo()
        currentUser = getAuthInstanceUseCase().currentUser
    }

    fun getUserInfo() {
        getAuthInstanceUseCase().currentUser?.let { user ->
            getUserInfoUseCase(user.uid).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = UserInfoState(info = result.data)
                    }
                    is Resource.Error -> {
                        _state.value = UserInfoState(error = result.message ?: ERROR_MESSAGE)
                    }
                    is Resource.Loading -> {
                        _state.value = UserInfoState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun signOut() {
        val currentUser = getAuthInstanceUseCase().currentUser
        currentUser?.let {
            if (it.isAnonymous) {
                it.delete()
            } else {
                getAuthInstanceUseCase().signOut()
            }
        }
    }
}