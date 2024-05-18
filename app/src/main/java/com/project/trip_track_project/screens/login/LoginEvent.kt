package com.project.trip_track_project.screens.login


sealed class LoginEvent {
    data class SetEmail(val email: String) : LoginEvent()
    data class SetPassword(val password: String) : LoginEvent()
    data object OnLogin : LoginEvent()
    data object ClearError : LoginEvent()
}