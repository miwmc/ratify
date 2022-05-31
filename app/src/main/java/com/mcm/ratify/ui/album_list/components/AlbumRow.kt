package com.mcm.ratify.ui.album_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mcm.ratify.domain.model.Album
import com.mcm.ratify.ui.Screen

@Composable
fun AlbumRow(
    rowIndex: Int,
    items: List<Album>,
    navController: NavController
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            AlbumItem(
                album = items[rowIndex * 2],
                modifier = Modifier.weight(1f),
                onItemClick = {
                    navController.navigate(
                        Screen.AlbumScreen.route + "/${it.album_id}/${it.title}"
                    )
                }
            )
            Spacer(modifier = Modifier.weight(0.1f))
            if (items.size >= rowIndex * 2 + 2) {
                AlbumItem(
                    album = items[rowIndex * 2 + 1],
                    modifier = Modifier.weight(1f),
                    onItemClick = {
                        navController.navigate(
                            Screen.AlbumScreen.route + "/${it.album_id}/${it.title}"
                        )
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