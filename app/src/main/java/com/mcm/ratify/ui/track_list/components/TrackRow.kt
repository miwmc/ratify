package com.mcm.ratify.ui.track_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mcm.ratify.domain.model.Track
import com.mcm.ratify.ui.Screen

@Composable
fun TrackRow(
    rowIndex: Int,
    items: List<Track>,
    navController: NavController
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            TrackItem(
                track = items[rowIndex * 2],
                modifier = Modifier.weight(1f),
                onItemClick = {
                    navController.navigate(Screen.TrackScreen.route + "/${it.track_id}")
                }
            )
            Spacer(modifier = Modifier.weight(0.1f))
            if (items.size >= rowIndex * 2 + 2) {
                TrackItem(
                    track = items[rowIndex * 2 + 1],
                    modifier = Modifier.weight(1f),
                    onItemClick = {
                        navController.navigate(Screen.TrackScreen.route + "/${it.track_id}")
                    }
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.weight(0.1f))
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}