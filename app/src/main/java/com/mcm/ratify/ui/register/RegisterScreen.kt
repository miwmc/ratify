package com.mcm.ratify.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcm.ratify.R
import com.mcm.ratify.ui.Screen
import com.mcm.ratify.ui.common.RatifyButton
import com.mcm.ratify.ui.common.RatifyTextField
import com.mcm.ratify.ui.navigation.TopNavigationBar
import com.mcm.ratify.util.MINIMUM_USERNAME_LENGTH

@Composable
fun RegisterScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    var username by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopNavigationBar(
                navController = navController
            )
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
                    input = username,
                    placeholder = stringResource(R.string.username),
                    onValueChange = {
                        username = it
                    },
                    keyboardType = KeyboardType.Text,
                )
                RatifyTextField(
                    input = email,
                    placeholder = stringResource(R.string.email),
                    onValueChange = {
                        email = it
                    },
                    keyboardType = KeyboardType.Email
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
                RatifyTextField(
                    input = confirmPassword,
                    placeholder = stringResource(R.string.confirm_password),
                    onValueChange = {
                        confirmPassword = it
                    },
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation()
                )
                RatifyButton(
                    text = stringResource(R.string.sign_up),
                    enabled = email.isNotBlank() &&
                            username.length >= MINIMUM_USERNAME_LENGTH &&
                            password.isNotBlank() &&
                            password == confirmPassword &&
                            !state.isLoading,
                    onClick = {
                        viewModel.signUpUser(username, email, password)
                    }
                )
            }
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