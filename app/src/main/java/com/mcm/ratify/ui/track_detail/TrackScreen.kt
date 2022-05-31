package com.mcm.ratify.ui.track_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import com.mcm.ratify.R
import com.mcm.ratify.ui.Screen
import com.mcm.ratify.ui.navigation.TopNavigationBar
import com.mcm.ratify.ui.common.RetrySection
import com.mcm.ratify.ui.theme.TextWhite
import com.mcm.ratify.ui.track_detail.components.StarWithLabelNav

@Composable
fun TrackScreen(
    navController: NavController,
    viewModel: TrackViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        state.track?.let { track ->
            GlideImage(
                imageModel = track.cover,
                contentScale = ContentScale.Crop,
                circularReveal = CircularReveal(duration = 500),
                failure = { Text(text = "Image request failed.") },
                modifier = Modifier
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0f, 0f, 0f, 0.7f))
            )
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopNavigationBar(
                    navController = navController,
                    color = TextWhite
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                ) {
                    Text(
                        text = track.title,
                        style = MaterialTheme.typography.h1,
                        color = TextWhite,
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = track.num_reviews.toString(),
                            style = MaterialTheme.typography.h2,
                            color = TextWhite,
                        )
                        Text(
                            text = stringResource(R.string.reviews_label),
                            style = MaterialTheme.typography.h2,
                            color = TextWhite,
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = track.rank.toString(),
                            style = MaterialTheme.typography.h2,
                            color = TextWhite,
                        )
                        Text(
                            text = stringResource(R.string.rank_label),
                            style = MaterialTheme.typography.h2,
                            color = TextWhite,
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star",
                            tint = TextWhite,
                            modifier = Modifier.scale(1.3f)
                        )
                        Text(
                            text = track.rating.toString(),
                            style = MaterialTheme.typography.h2,
                            color = TextWhite,
                        )
                    }
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                ) {
                    Text(
                        text = track.artist_name,
                        style = MaterialTheme.typography.h2,
                        color = TextWhite,
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                ) {
                    StarWithLabelNav(
                        labelText = stringResource(R.string.track_reviews),
                        track = track,
                        onClick = {
                            navController.navigate(
                                Screen.ReviewsScreen.route + "/${it.track_id}"
                            )
                        }
                    )
                    if (!track.isReviewedByUser &&
                        !viewModel.getAuthInstanceUseCase().currentUser?.isAnonymous!!
                    ) {
                        StarWithLabelNav(
                            labelText = stringResource(R.string.write_your_review),
                            track = track,
                            onClick = {
                                navController.navigate(
                                    Screen.WriteReviewScreen.route + "/${it.track_id}"
                                )
                            }
                        )
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (state.error.isNotBlank()) {
                RetrySection(
                    error = stringResource(id = R.string.unknown_error)
                ) {

                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .scale(2f)
                        .align(Alignment.Center),
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}