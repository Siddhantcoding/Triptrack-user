package com.project.trip_track_project.screens.register


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