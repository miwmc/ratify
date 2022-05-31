package com.mcm.ratify.ui.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import com.mcm.ratify.domain.use_case.GetAuthInstanceUseCase
import com.mcm.ratify.domain.use_case.GoogleSignInUseCase
import com.mcm.ratify.domain.use_case.SignInAsGuestUseCase
import com.mcm.ratify.domain.use_case.SignInUserUseCase
import com.mcm.ratify.util.ERROR_MESSAGE
import com.mcm.ratify.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val getAuthInstanceUseCase: GetAuthInstanceUseCase,
    val googleSignInClient: GoogleSignInClient,
    private val signInUserUseCase: SignInUserUseCase,
    private val signInAsGuestUseCase: SignInAsGuestUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase
) : ViewModel() {

    private val _state = mutableStateOf(FirebaseUserState())
    val state: State<FirebaseUserState> = _state

    fun signInUser(email: String, password: String) {
        signInUserUseCase(email, password).onEach { result ->
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

    fun signInAsGuest() {
        signInAsGuestUseCase().onEach { result ->
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

    fun googleSignInUser(googleSignInAccount: GoogleSignInAccount) {
        googleSignInUseCase(googleSignInAccount).onEach { result ->
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