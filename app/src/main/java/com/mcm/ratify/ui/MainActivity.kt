package com.mcm.ratify.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mcm.ratify.util.*
import dagger.hilt.android.AndroidEntryPoint
import com.mcm.ratify.ui.Screen
import com.mcm.ratify.ui.main.MainScreen
import com.mcm.ratify.ui.about.AboutScreen
import com.mcm.ratify.ui.album_list.GenreScreen
import com.mcm.ratify.ui.browse.BrowseScreen
import com.mcm.ratify.ui.edit_profile.EditProfileScreen
import com.mcm.ratify.ui.help.HelpScreen
import com.mcm.ratify.ui.home.HomeScreen
import com.mcm.ratify.ui.login.LoginScreen
import com.mcm.ratify.ui.navigation.BottomNavigationBarComposable
import com.mcm.ratify.ui.profile.ProfileScreen
import com.mcm.ratify.ui.register.RegisterScreen
import com.mcm.ratify.ui.review_list.ReviewsScreen
import com.mcm.ratify.ui.theme.RatifyTheme
import com.mcm.ratify.ui.track_detail.TrackScreen
import com.mcm.ratify.ui.track_list.AlbumScreen
import com.mcm.ratify.ui.write_review.WriteReviewScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RatifyTheme(darkTheme = true) {
                MainNavigation()
            }
        }
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            when (currentDestination) {
                Screen.BrowseScreen.route,
                Screen.HomeScreen.route,
                Screen.ProfileScreen.route ->
                    BottomNavigationBarComposable(navController = navController)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = Screen.MainScreen.route
            ) {
                composable(
                    route = Screen.MainScreen.route
                ) {
                    MainScreen(
                        navController = navController
                    )
                }
                composable(
                    route = Screen.LoginScreen.route
                ) {
                    LoginScreen(
                        navController = navController,
                        scaffoldState = scaffoldState
                    )
                }
                composable(
                    route = Screen.RegisterScreen.route
                ) {
                    RegisterScreen(
                        navController = navController,
                        scaffoldState = scaffoldState
                    )
                }
                composable(
                    route = Screen.HomeScreen.route
                ) {
                    HomeScreen(
                        navController = navController
                    )
                }
                composable(
                    route = Screen.BrowseScreen.route
                ) {
                    BrowseScreen(
                        navController = navController
                    )
                }
                composable(
                    route = Screen.ProfileScreen.route
                ) {
                    ProfileScreen(
                        navController = navController
                    )
                }
                composable(
                    route = Screen.GenreScreen.route + "/{$PARAM_GENRE_ID}/{$PARAM_GENRE_NAME}"
                ) {
                    GenreScreen(
                        navController = navController
                    )
                }
                composable(
                    route = Screen.AlbumScreen.route + "/{$PARAM_ALBUM_ID}/{$PARAM_ALBUM_NAME}"
                ) {
                    AlbumScreen(
                        navController = navController
                    )
                }
                composable(
                    route = Screen.TrackScreen.route + "/{$PARAM_TRACK_ID}"
                ) {
                    TrackScreen(
                        navController = navController
                    )
                }
                composable(
                    route = Screen.ReviewsScreen.route + "/{$PARAM_TRACK_ID}"
                ) {
                    ReviewsScreen(
                        navController = navController
                    )
                }
                composable(
                    route = Screen.WriteReviewScreen.route + "/{$PARAM_TRACK_ID}"
                ) {
                    WriteReviewScreen(
                        navController = navController,
                        scaffoldState = scaffoldState
                    )
                }
                composable(
                    route = Screen.EditProfileScreen.route
                ) {
                    EditProfileScreen(
                        navController = navController,
                        scaffoldState = scaffoldState
                    )
                }
                composable(
                    route = Screen.HelpScreen.route
                ) {
                    HelpScreen(
                        navController = navController
                    )
                }
                composable(
                    route = Screen.AboutScreen.route
                ) {
                    AboutScreen(
                        navController = navController
                    )
                }
            }
        }
    }
}