package com.mcm.ratify.ui.review_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcm.ratify.R
import com.mcm.ratify.ui.common.RetrySection
import com.mcm.ratify.ui.navigation.TopNavigationBar
import com.mcm.ratify.ui.common.ReviewItem

@Composable
fun ReviewsScreen(
    navController: NavController,
    viewModel: TrackReviewsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        state.list?.let { reviewList ->
            Column(modifier = Modifier.fillMaxSize()) {
                TopNavigationBar(
                    navController = navController,
                    text = stringResource(R.string.track_reviews)
                )
                LazyColumn(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(count = reviewList.size) {
                        ReviewItem(index = it, items = reviewList)
                    }
                }
                if (reviewList.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No reviews available.",
                            style = MaterialTheme.typography.h2
                        )
                    }
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0f, 0f, 0f, 0.6f))
            ) {
                RetrySection(
                    modifier = Modifier.align(Alignment.Center),
                    error = stringResource(id = R.string.unknown_error)
                ) {

                }
            }
        }
    }
}