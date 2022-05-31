package com.mcm.ratify.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TopNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    text: String = "",
    color: Color = MaterialTheme.colors.onBackground,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.10f),
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Arrow",
            tint = color,
            modifier = modifier
                .fillMaxWidth(0.05f)
                .scale(2f)
                .clickable {
                    navController.popBackStack()
                }
        )
        Text(
            text = text,
            style = MaterialTheme.typography.h2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier
                .fillMaxWidth(0.7f)
        )
        Spacer(
            modifier = modifier
                .width(6.dp)
        )
        Spacer(
            modifier = modifier
                .width(6.dp)
        )
    }
}