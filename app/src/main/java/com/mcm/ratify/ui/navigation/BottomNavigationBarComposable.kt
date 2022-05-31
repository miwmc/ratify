package com.mcm.ratify.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mcm.ratify.R
import com.mcm.ratify.ui.Screen

@Composable
fun BottomNavigationBarComposable(
    navController: NavController
) {
    BottomNavigationBar(
        items = listOf(
            BottomNavItem(
                name = stringResource(id = R.string.browse),
                route = Screen.BrowseScreen.route,
                icon = Icons.Default.Explore
            ),
            BottomNavItem(
                name = stringResource(id = R.string.home),
                route = Screen.HomeScreen.route,
                icon = Icons.Default.Home
            ),
            BottomNavItem(
                name = stringResource(id = R.string.profile),
                route = Screen.ProfileScreen.route,
                icon = Icons.Default.Person
            )
        ),
        navController = navController,
        onItemClick = {
            navController.navigate(it.route)
        }
    )
}