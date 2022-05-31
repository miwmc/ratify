package com.mcm.ratify.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import com.mcm.ratify.domain.model.Review

@Composable
fun ReviewItem(
    index: Int,
    items: List<Review>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.primary)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(modifier = Modifier.fillMaxSize(0.2f)) {
                    GlideImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .shadow(1.dp, RoundedCornerShape(10)),
                        imageModel = items[index].user_photo,
                        contentScale = ContentScale.Crop,
                        circularReveal = CircularReveal(duration = 500),
                        failure = { Text(text = "Image request failed.") },
                    )
                }
                Column() {
                    Text(
                        text = items[index].track_title,
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = items[index].artist_name,
                        style = MaterialTheme.typography.body1
                    )
                }
                Column() {
                    Text(
                        text = items[index].username,
                        style = MaterialTheme.typography.body1
                    )
                    Row() {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star",
                            tint = MaterialTheme.colors.onBackground
                        )
                        Text(
                            text = items[index].rating.toString(),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
            Text(
                text = items[index].content,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Justify
            )
        }
    }
}