package com.example.trip_track_project.Screens.Login


sealed class LoginEvent {
    data class Onlogin(val email: String, val password: String) : LoginEvent()
    data class SetEmail(val email: String) : LoginEvent()
    data class SetPassword(val password: String) : LoginEvent()
    data object OnLogin : LoginEvent()
    data object ClearError : LoginEvent()
}