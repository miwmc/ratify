package com.mcm.ratify.ui.browse.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import com.mcm.ratify.domain.model.Genre
import com.mcm.ratify.ui.theme.TextWhite

@Composable
fun GenreItem(
    genre: Genre,
    onItemClick: (Genre) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .shadow(2.dp)
            .aspectRatio(1f)
            .clickable {
                onItemClick(genre)
            }
    ) {
        GlideImage(
            imageModel = genre.picture,
            contentScale = ContentScale.Crop,
            circularReveal = CircularReveal(duration = 500),
            failure = { Text(text = "Image request failed.") },
            modifier = modifier
                .fillMaxSize()
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter)
                .background(Color(0f, 0f, 0f, 0.6f))
        ) {
            Text(
                text = genre.name,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = TextWhite,
                modifier = modifier
                    .fillMaxWidth()
            )
        }
    }
}