package com.mcm.ratify.ui.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.common.api.ApiException
import com.mcm.ratify.R
import com.mcm.ratify.remote.firebase.AuthResultContract
import com.mcm.ratify.ui.Screen
import com.mcm.ratify.ui.common.RatifyButton
import com.mcm.ratify.ui.common.RatifyTextField
import com.mcm.ratify.ui.login.components.SignInButton
import com.mcm.ratify.util.SIGN_IN_REQUEST_CODE

@Composable
fun LoginScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val authResultLauncher =
        rememberLauncherForActivityResult(
            contract = AuthResultContract(viewModel.googleSignInClient)
        ) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                account?.let { viewModel.googleSignInUser(it) }
            } catch (e: Exception) {

            }
        }

    LaunchedEffect(key1 = true) {
        viewModel.getAuthInstanceUseCase().currentUser?.let {
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(Screen.HomeScreen.route)
            }
        }
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo"
            )
            RatifyTextField(
                input = email,
                placeholder = stringResource(R.string.email),
                onValueChange = {
                    email = it
                },
                keyboardType = KeyboardType.Email,
            )
            RatifyTextField(
                input = password,
                placeholder = stringResource(R.string.password),
                onValueChange = {
                    password = it
                },
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation()
            )
            RatifyButton(
                text = stringResource(id = R.string.login),
                enabled = email.isNotEmpty() && password.isNotEmpty() && !state.isLoading
            ) {
                viewModel.signInUser(email, password)
            }
            SignInButton(
                text = stringResource(R.string.sign_in_with_google),
                icon = painterResource(id = R.drawable.googleg_standard_color_18),
                isLoading = state.isLoading
            ) {
                authResultLauncher.launch(SIGN_IN_REQUEST_CODE)
            }
            Text(
                text = stringResource(R.string.i_dont_have_an_account),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screen.RegisterScreen.route)
                    }
            )
            Text(
                text = stringResource(R.string.continue_as_guest),
                modifier = Modifier
                    .clickable {
                        viewModel.signInAsGuest()
                    }
            )
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0f, 0f, 0f, 0.6f))
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .scale(2f)
                        .align(Alignment.Center),
                    color = MaterialTheme.colors.primary
                )
            }
        }

        if (state.error.isNotBlank()) {
            LaunchedEffect(key1 = state.error) {
                scaffoldState.snackbarHostState.showSnackbar(state.error)
            }
        }

        state.user?.let {
            LaunchedEffect(key1 = state.user) {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.HomeScreen.route)
                }
            }
        }
    }
}