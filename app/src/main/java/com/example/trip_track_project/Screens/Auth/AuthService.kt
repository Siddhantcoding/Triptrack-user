package com.example.trip_track_project.Screens.Auth

import android.util.Log
import com.example.trip_track_project.Register.RegisterState
import com.example.trip_track_project.Screens.Login.LoginState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


class AuthService(
    private val auth: FirebaseAuth = Firebase.auth,
    private val db : FirebaseFirestore = Firebase.firestore
) {
    fun login(state: MutableStateFlow<LoginState>) {
        Log.d("AuthService", "login: ${state.value.email} ${state.value.password}")
        auth.signInWithEmailAndPassword(state.value.email, state.value.password)
            .addOnFailureListener {
                state.update { state ->
                    state.copy(error = it.message ?: "An error occurred", isLoading = false)
                }
            }.addOnSuccessListener {
                state.update { state ->
                    state.copy(isLoginSuccess = true, isLoading = false, error = "")
                }
            }
    }

    fun register(state: MutableStateFlow<RegisterState>) {
        auth.createUserWithEmailAndPassword(state.value.email, state.value.password)
            .addOnFailureListener {
                state.update { state ->
                    state.copy(error = it.message ?: "An error occurred", isLoading = false)
                }
            }.addOnSuccessListener {
                state.update { state ->
                    state.copy(isRegisterSuccess = true, isLoading = false, error = "")
                }
                val profileUpdates = userProfileChangeRequest { displayName = state.value.username }
                it.user?.updateProfile(profileUpdates) // create a firestore collection to store this information into user collection
                db.collection("users").document(it.user?.uid ?: "").set(hashMapOf("name" to state.value.username))

            }
    }
}