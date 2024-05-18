package com.project.trip_track_project.screens.login


data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val isLoginSuccess: Boolean = false,
)