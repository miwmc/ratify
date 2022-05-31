package com.mcm.ratify.ui.album_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcm.ratify.R
import com.mcm.ratify.ui.album_list.GenreViewModel
import com.mcm.ratify.ui.common.RetrySection
import com.mcm.ratify.ui.navigation.TopNavigationBar

@Composable
fun AlbumList(
    navController: NavController,
    viewModel: GenreViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val genreName by remember {
        mutableStateOf(viewModel.genreName)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopNavigationBar(
                navController = navController,
                text = genreName
            )
            LazyColumn {
                val itemCount = if (state.albums.size % 2 == 0) {
                    state.albums.size / 2
                } else {
                    state.albums.size / 2 + 1
                }
                items(count = itemCount) {
                    AlbumRow(
                        rowIndex = it,
                        items = state.albums,
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