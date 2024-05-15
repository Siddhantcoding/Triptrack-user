package com.example.trip_track_project

import RegisterScreen
import UserScreen2
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trip_track_project.Screens.Login.LoginScreen
import com.example.trip_track_project.Screens.userscreens.UserScreen

enum class MainScreen(val route: String) {
    Home("home"),
    MapView("map")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController  , startDestination = MainScreen.Home.route) {
        composable(MainScreen.Home.route) {
            UserScreen()
        }
        composable(MainScreen.MapView.route) {
            UserScreen2()
        }
    }




}