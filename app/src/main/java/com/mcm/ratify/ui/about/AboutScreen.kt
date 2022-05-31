package com.mcm.ratify.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mcm.ratify.R
import com.mcm.ratify.ui.navigation.TopNavigationBar

@Composable
fun AboutScreen(
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopNavigationBar(navController = navController)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(0.3f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.image),
                        contentDescription = "logo",
                        modifier = Modifier
                            .shadow(5.dp, RoundedCornerShape(10))
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = stringResource(R.string.name_label),
                        style = MaterialTheme.typography.h3
                    )
                    Text(
                        text = stringResource(R.string.my_name),
                        style = MaterialTheme.typography.h3
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colors.onBackground)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = stringResource(R.string.number_label),
                        style = MaterialTheme.typography.h3
                    )
                    Text(
                        text = stringResource(R.string.my_number),
                        style = MaterialTheme.typography.h3
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colors.onBackground)
                    )
                }
            }
        }
    }
}
