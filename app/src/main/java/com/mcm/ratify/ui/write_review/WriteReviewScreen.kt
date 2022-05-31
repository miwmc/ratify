package com.mcm.ratify.ui.write_review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcm.ratify.R
import com.mcm.ratify.ui.Screen
import com.mcm.ratify.ui.common.RatifyButton
import com.mcm.ratify.ui.common.RatifyTextField
import com.mcm.ratify.ui.navigation.TopNavigationBar
import com.mcm.ratify.util.MINIMUM_REVIEW_CONTENT_LENGTH

@Composable
fun WriteReviewScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: WriteReviewViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    var content by remember {
        mutableStateOf("")
    }
    var rating by remember {
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
                RatifyTextField(
                    modifier = Modifier.fillMaxWidth(0.2f),
                    input = rating,
                    placeholder = stringResource(R.string.rating_placeholder),
                    onValueChange = {
                        rating = it
                        if (!it.isDigitsOnly()) {
                            rating = "0"
                        }
                        if (it.length > 1) {
                            rating = "10"
                        }
                    },
                    singleLine = true,
                    keyboardType = KeyboardType.Number,
                )
                RatifyTextField(
                    modifier = Modifier.fillMaxHeight(0.5f),
                    input = content,
                    placeholder = stringResource(R.string.write_your_review_placeholder),
                    onValueChange = {
                        content = it
                    },
                    singleLine = false,
                    keyboardType = KeyboardType.Text,
                )
                RatifyButton(
                    text = stringResource(R.string.submit),
                    enabled = content.length >= MINIMUM_REVIEW_CONTENT_LENGTH
                            && !state.isLoading
                ) {
                    viewModel.writeReview(rating.toInt(), content)
                }
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

        if (state.isDone) {
            LaunchedEffect(key1 = state.isDone) {
                navController.popBackStack()
                navController.navigate(Screen.TrackScreen.route +
                        "/${viewModel.trackId}")
            }
        }
    }
}