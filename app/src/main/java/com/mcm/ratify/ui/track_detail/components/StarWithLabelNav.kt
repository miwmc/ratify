package com.mcm.ratify.ui.track_detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.mcm.ratify.domain.model.Track
import com.mcm.ratify.ui.theme.TextWhite

@Composable
fun StarWithLabelNav(
    labelText: String,
    track: Track,
    onClick: (Track) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(track)
            }
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Star",
            tint = TextWhite,
            modifier = Modifier
                .scale(2f)
                .weight(0.35f)
        )
        Text(
            text = labelText,
            style = MaterialTheme.typography.h2,
            color = TextWhite,
            modifier = Modifier.weight(0.65f)
        )
    }
}