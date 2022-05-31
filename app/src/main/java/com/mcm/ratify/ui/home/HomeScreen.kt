package com.mcm.ratify.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcm.ratify.R
import com.mcm.ratify.ui.common.RetrySection
import com.mcm.ratify.ui.common.ReviewItem

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f)
            ) {
                Spacer(
                    modifier = Modifier
                        .width(5.dp)
                        .weight(0.1f)
                )
                Text(
                    text = stringResource(R.string.home_label),
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier
                        .weight(1f)
                )
                Spacer(
                    modifier = Modifier
                        .width(5.dp)
                        .weight(0.1f)
                )
            }
            state.list?.let { reviewList ->
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
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
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
                        viewModel.getHomeReviews()
                    }
                }
            }
        }
    }
}