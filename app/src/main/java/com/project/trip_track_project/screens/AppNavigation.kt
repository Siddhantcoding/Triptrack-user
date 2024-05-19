package com.project.trip_track_project.screens


import android.content.Intent
import android.location.Geocoder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.project.trip_track_project.AuthActivity
import com.project.trip_track_project.screens.location.LocationScreen
import com.project.trip_track_project.screens.location.LocationViewModel
import com.project.trip_track_project.screens.profile.ProfileScreen
import com.project.trip_track_project.screens.profile.ProfileViewModel
import com.project.trip_track_project.screens.userscreens.UserScreen
import com.project.trip_track_project.screens.userscreens.UserScreenEvent
import com.project.trip_track_project.screens.userscreens.UserViewModel

enum class MainScreen(val route: String) {
    Home("home"),
    MapView("map"),
    Profile("profile"),
    FindTaxi("taxi"),
    FindRickshaw("erickshaw"),
    FindBus("bus")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val auth: FirebaseAuth = Firebase.auth
    val db: FirebaseFirestore = Firebase.firestore
    val geocoder = Geocoder(context, context.resources.configuration.locales[0])
    NavHost(navController, startDestination = MainScreen.Home.route) {
        composable(MainScreen.Home.route) {
            val userVM: UserViewModel = viewModel()
            UserScreen(
                modifier = Modifier,
                state = userVM.state.collectAsState().value,
                onEvent = { event ->
                    when (event) {
                        is UserScreenEvent.onProfileSelected -> {
                            navController.navigate(MainScreen.Profile.route)
                        }

                        is UserScreenEvent.onLocationSelected -> {
                            navController.navigate(MainScreen.MapView.route)
                        }


                        is UserScreenEvent.onTaxiSelected -> {
                            navController.navigate(MainScreen.FindTaxi.route)
                        }

                        is UserScreenEvent.onRickshawSelected -> {
                            navController.navigate(MainScreen.FindRickshaw.route)
                        }

                        is UserScreenEvent.onBusSelected -> {
                            navController.navigate(MainScreen.FindBus.route)
                        }

                        is UserScreenEvent.onLogoutSelected -> {
                            auth.signOut()
                            val i = Intent(context, AuthActivity::class.java)
                            context.startActivity(i)
                        }
                    }
                }
            )
        }
        composable(MainScreen.MapView.route) {
            val locationVM: LocationViewModel = viewModel(factory = LocationViewModel.Factory(geocoder))
            LocationScreen(
                state = locationVM.state.collectAsState().value,
            )
        }
        composable(MainScreen.Profile.route) {
            val profileVM: ProfileViewModel = viewModel()
            ProfileScreen(
                state = profileVM.state.collectAsState().value,
                onEvent = profileVM::onEvent,
                onBack = { navController.popBackStack() }
            )
        }
        composable(MainScreen.FindTaxi.route) {
            //TaxiScreen()
        }
        composable(MainScreen.FindRickshaw.route) {
            //RickshawScreen()
        }
        composable(MainScreen.FindBus.route) {
            //BusScreen()
        }

    }


}