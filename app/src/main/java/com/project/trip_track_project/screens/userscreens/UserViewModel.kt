package com.project.trip_track_project.screens.userscreens

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.trip_track_project.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


sealed class UserScreenEvent {
    data object onProfileSelected : UserScreenEvent()
    data object onLocationSelected : UserScreenEvent()
    data object onLogoutSelected : UserScreenEvent()
    data object onTaxiSelected : UserScreenEvent()
    data object onRickshawSelected : UserScreenEvent()
    data object onBusSelected : UserScreenEvent()

}

data class UserScreenState(
    val user: User = User(),
)

class UserViewModel(
    val db: FirebaseAuth = Firebase.auth,
    val auth: FirebaseAuth = Firebase.auth,
) : ViewModel() {
    private val _state: MutableStateFlow<UserScreenState> = MutableStateFlow(UserScreenState())
    val state: StateFlow<UserScreenState> = _state.asStateFlow()
}