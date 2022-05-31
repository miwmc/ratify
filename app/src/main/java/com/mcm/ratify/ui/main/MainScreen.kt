package com.mcm.ratify.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mcm.ratify.R
import com.mcm.ratify.ui.Screen
import kotlinx.coroutines.delay

@Composable
fun MainScreen(
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(1.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .scale(2f),
                    color = MaterialTheme.colors.primary
                )
                Text(
                    text = stringResource(R.string.loading_label),
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.height(1.dp))
            }
        }
        LaunchedEffect(key1 = true) {
            delay(10000L)
            if (Firebase.auth.currentUser != null) {
                navController.popBackStack()
                navController.navigate(Screen.HomeScreen.route)
            } else {
                navController.popBackStack()
                navController.navigate(Screen.LoginScreen.route)
            }
        }
    }
}