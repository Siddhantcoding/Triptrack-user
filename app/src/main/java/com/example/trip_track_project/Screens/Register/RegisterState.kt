package com.example.trip_track_project.Screens.Register


data class RegisterState(
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val confirmPassword: String = "",
    var isLoading: Boolean = false,
    var error: String = "",
    var isRegisterSuccess: Boolean = false,
) {
}