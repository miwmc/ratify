package com.mcm.ratify.ui.browse.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mcm.ratify.domain.model.Genre
import com.mcm.ratify.ui.Screen

@Composable
fun GenreRow(
    rowIndex: Int,
    items: List<Genre>,
    navController: NavController
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            GenreItem(
                genre = items[rowIndex * 2],
                modifier = Modifier.weight(1f),
                onItemClick = {
                    navController.navigate(
                        Screen.GenreScreen.route + "/${it.genre_id}/${it.name}"
                    )
                }
            )
            Spacer(modifier = Modifier.weight(0.1f))
            if (items.size >= rowIndex * 2 + 2) {
                GenreItem(
                    genre = items[rowIndex * 2 + 1],
                    modifier = Modifier.weight(1f),
                    onItemClick = {
                        navController.navigate(
                            Screen.GenreScreen.route + "/${it.genre_id}/${it.name}"
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