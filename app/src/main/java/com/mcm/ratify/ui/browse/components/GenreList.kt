package com.mcm.ratify.ui.browse.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcm.ratify.R
import com.mcm.ratify.ui.browse.BrowseViewModel
import com.mcm.ratify.ui.common.RetrySection

@Composable
fun GenreList(
    navController: NavController,
    viewModel: BrowseViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize()) {
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
                    text = stringResource(R.string.browse_title),
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
            LazyColumn {
                val itemCount = if (state.genres.size % 2 == 0) {
                    state.genres.size / 2
                } else {
                    state.genres.size / 2 + 1
                }
                items(count = itemCount) {
                    GenreRow(
                        rowIndex = it,
                        items = state.genres,
                        navController = navController
                    )
                }
            }
        }
        if (state.error.isNotBlank()) {
            RetrySection(
                error = stringResource(id = R.string.unknown_error)
            ) {
                viewModel.getGenreList()
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