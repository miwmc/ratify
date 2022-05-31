package com.mcm.ratify.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mcm.ratify.R
import com.mcm.ratify.ui.Screen

@Composable
fun RatifyButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(0.5f),
        border = BorderStroke(1.dp, Color.Gray),
        enabled = enabled,
        onClick = onClick
    ) {
        Text(
            text = text,
            color = Color.Gray,
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center
        )
    }
}