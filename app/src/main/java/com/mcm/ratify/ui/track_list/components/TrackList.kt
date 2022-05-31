package com.mcm.ratify.ui.track_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcm.ratify.R
import com.mcm.ratify.ui.common.RetrySection
import com.mcm.ratify.ui.navigation.TopNavigationBar
import com.mcm.ratify.ui.track_list.AlbumViewModel

@Composable
fun TrackList(
    navController: NavController,
    viewModel: AlbumViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val albumName by remember {
        mutableStateOf(viewModel.albumName)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopNavigationBar(
                navController = navController,
                text = albumName
            )
            LazyColumn {
                val itemCount = if (state.tracks.size % 2 == 0) {
                    state.tracks.size / 2
                } else {
                    state.tracks.size / 2 + 1
                }
                items(count = itemCount) {
                    TrackRow(
                        rowIndex = it,
                        items = state.tracks,
                        navController = navController
                    )
                }
            }
        }
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