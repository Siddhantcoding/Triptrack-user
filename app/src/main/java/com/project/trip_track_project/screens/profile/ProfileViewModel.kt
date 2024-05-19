package com.project.trip_track_project.screens.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.project.trip_track_project.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class ProfileStatus {
    IDLE,
    LOADING,
    SUCCESS,
    ERROR
}

data class ProfileState(
    // status fields
    val status: ProfileStatus = ProfileStatus.IDLE,
    val message: String = "",
    // form fields
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val phone: String = "",
    //
    val user: User = User(),
)

sealed class ProfileEvent {
    data object SaveProfile : ProfileEvent()
    data object ClearForm : ProfileEvent()
    data object GetUserProfile : ProfileEvent()
    data class OnFirstNameChange(val firstname: String) : ProfileEvent()
    data class OnLastNameChange(val lastname: String) : ProfileEvent()
    data class OnEmailChange(val email: String) : ProfileEvent()
    data class OnPhoneChange(val phone: String) : ProfileEvent()
}

class ProfileViewModel(
    val auth: FirebaseAuth = Firebase.auth,
    private val db: FirebaseFirestore = Firebase.firestore,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        clearForm()
        getUserProfile()

    }

    private fun getUserProfile() {
        _state.update { it.copy(status = ProfileStatus.LOADING) }
        db.collection("users")
            .document(auth.currentUser?.uid!!)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val user = document.toObject(User::class.java)
                    _state.update {
                        it.copy(
                            user = user!!,
                            status = ProfileStatus.IDLE,
                            firstname = user.firstname,
                            lastname = user.lastname,
                            email = user.email,
                            phone = user.phone,
                        )
                    }
                }
            }.addOnFailureListener { exception ->
                _state.update {
                    it.copy(
                        status = ProfileStatus.ERROR,
                        message = "No Profile Data Found",
                        user = User(email = auth.currentUser?.email!!) // default user with email
                    )
                }
            }
    }

    private fun clearForm() {
        _state.update {
            it.copy(
                firstname = "",
                lastname = "",
                email = "",
                phone = "",
            )
        }
    }

    private fun saveProfile() {
        _state.update { it.copy(status = ProfileStatus.LOADING) }
        val user = User(
            firstname = state.value.firstname,
            lastname = state.value.lastname,
            email = state.value.email,
            phone = state.value.phone,
            createdAt = System.currentTimeMillis().toString(),
        )
        db.collection("users")
            .document(auth.currentUser?.uid!!)
            .update(
                "firstname", user.firstname,
                "lastname", user.lastname,
                "email", user.email,
                "phone", user.phone,
                "createdAt", user.createdAt,
            ).addOnSuccessListener {
                _state.update {
                    it.copy(
                        status = ProfileStatus.SUCCESS,
                        message = "Profile Updated Successfully",
                        user = user
                    )
                }
            }.addOnFailureListener { exception ->
                _state.update {
                    it.copy(
                        status = ProfileStatus.ERROR,
                        message = "Error Updating Profile",
                        user = user
                    )
                }
            }

    }

    private fun onFirstNameChange(firstname: String) {
        _state.update { it.copy(firstname = firstname) }
    }
    private fun onLastNameChange(lastname: String) {
        _state.update { it.copy(lastname = lastname) }
    }
    private fun onEmailChange(email: String) {
        _state.update { it.copy(email = email) }
    }
    private fun onPhoneChange(phone: String) {
        _state.update { it.copy(phone = phone) }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.SaveProfile -> saveProfile()
            is ProfileEvent.ClearForm -> clearForm()
            is ProfileEvent.GetUserProfile -> getUserProfile()
            is ProfileEvent.OnFirstNameChange -> onFirstNameChange(event.firstname)
            is ProfileEvent.OnLastNameChange -> onLastNameChange(event.lastname)
            is ProfileEvent.OnEmailChange -> onEmailChange(event.email)
            is ProfileEvent.OnPhoneChange -> onPhoneChange(event.phone)
        }
    }
}