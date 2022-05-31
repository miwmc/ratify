package com.mcm.ratify.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import com.mcm.ratify.R
import com.mcm.ratify.ui.Screen
import com.mcm.ratify.ui.common.RetrySection
import com.mcm.ratify.ui.theme.BronzeStar
import com.mcm.ratify.ui.theme.GoldStar
import com.mcm.ratify.ui.theme.SilverStar
import com.mcm.ratify.util.FREE_TYPE
import com.mcm.ratify.util.GUEST_TYPE
import com.mcm.ratify.util.PREMIUM_TYPE

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    var expanded by remember {
        mutableStateOf(false)
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        state.info?.let {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        navController.navigate(Screen.HelpScreen.route)
                    }
                ) {
                    Text("Help")
                }
                Divider()
                DropdownMenuItem(
                    onClick = {
                        navController.navigate(Screen.AboutScreen.route)
                    }
                ) {
                    Text("About")
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.scale(1f))
                    Icon(
                        imageVector = Icons.Default.Help,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .scale(2f)
                            .padding(5.dp)
                            .clickable {
                                expanded = true
                            }
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(0.3f)
                ) {
                    GlideImage(
                        imageModel = state.info.photo_url,
                        contentScale = ContentScale.Crop,
                        circularReveal = CircularReveal(duration = 500),
                        failure = { Text(text = "Image request failed.") },
                        modifier = Modifier
                            .fillMaxSize()
                            .shadow(5.dp, RoundedCornerShape(10))
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = state.info.username,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "medal",
                        tint =
                        when (state.info.type) {
                            PREMIUM_TYPE -> GoldStar
                            FREE_TYPE -> SilverStar
                            GUEST_TYPE -> BronzeStar
                            else -> MaterialTheme.colors.onBackground
                        },
                        modifier = Modifier
                            .scale(2f)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = stringResource(R.string.member_since),
                        style = MaterialTheme.typography.h3
                    )
                    Text(
                        text = state.info.date_joined.toDate().toString(),
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
                        text = stringResource(R.string.tracks_rated),
                        style = MaterialTheme.typography.h3
                    )
                    Text(
                        text = state.info.tracks_reviewed.toString(),
                        style = MaterialTheme.typography.h3
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colors.onBackground)
                    )
                }
                if (viewModel.currentUser != null) {
                    if (viewModel.currentUser.email != null &&
                        !state.info.is_anonymous
                    ) {
                        Text(
                            text = stringResource(R.string.edit_profile),
                            style = MaterialTheme.typography.h3,
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(Screen.EditProfileScreen.route)
                                }
                        )
                    }
                }
                Text(
                    text = stringResource(R.string.sign_out),
                    textDecoration = TextDecoration.Underline,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier
                        .clickable {
                            viewModel.signOut()
                            navController.navigate(Screen.LoginScreen.route) {
                                popUpTo(Screen.LoginScreen.route)
                            }
                        }
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.error.isNotBlank()) {
                RetrySection(
                    error = stringResource(id = R.string.unknown_error)
                ) {
                    viewModel.getUserInfo()
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
}