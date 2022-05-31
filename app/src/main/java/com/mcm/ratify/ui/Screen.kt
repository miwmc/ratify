package com.mcm.ratify.ui

sealed class Screen(val route : String) {
    object MainScreen : Screen("main_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object HomeScreen : Screen("home_screen")
    object BrowseScreen : Screen("browse_screen")
    object GenreScreen : Screen("genre_screen")
    object AlbumScreen : Screen("album_screen")
    object TrackScreen : Screen("track_screen")
    object ReviewsScreen : Screen("reviews_screen")
    object WriteReviewScreen : Screen("write_review_screen")
    object ProfileScreen : Screen("profile_screen")
    object EditProfileScreen : Screen("edit_profile_screen")
    object HelpScreen : Screen("help_screen")
    object AboutScreen : Screen("about_screen")
}
