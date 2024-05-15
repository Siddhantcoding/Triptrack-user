package com.example.trip_track_project.Screens

import RegisterScreen
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trip_track_project.MainActivity
import com.example.trip_track_project.Screens.Login.LoginScreen
import com.example.trip_track_project.Screens.Login.LoginViewModel
import com.example.trip_track_project.Screens.Register.RegisterViewModel

enum class AuthScreen(val route : String){
    Login("Login"),
    Register("Register"),
}


@Composable
fun AuthNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = AuthScreen.Login.route) {
        composable(AuthScreen.Login.route) {
            val vm: LoginViewModel = viewModel()
            LoginScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onNavigateToRegister = { navController.navigate(AuthScreen.Register.route) },
                gotoHome = { navigateToMain(context) }
            )
        }
        composable(AuthScreen.Register.route) {
            val vm: RegisterViewModel = viewModel()
            RegisterScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onBack = {
                    navController.popBackStack()
                }
            ) {
                navigateToMain(context)
            }
        }
    }

}

fun navigateToMain(context: Context) {
    val i = Intent(context, MainActivity::class.java)
    context.startActivity(i)
}
