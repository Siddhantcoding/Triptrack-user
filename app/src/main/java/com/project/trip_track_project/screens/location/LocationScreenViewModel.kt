package com.project.trip_track_project.screens.location


import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class VEHICLE_CHOICES {
    TAXI,
    RICKSHAW,
    BUS,
    ALL
}

data class LocationState(
    val driverList: List<Driver> = emptyList(),
    val selectedVehicle: VEHICLE_CHOICES = VEHICLE_CHOICES.ALL,
    val selectedDriver: Driver = Driver(),
    val user: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val currentLocation: String = "",
)

class StopPoint(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = ""
)
data class Driver(
    val user: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = "",
    val vehicle : String = "",
    val stops: List<StopPoint> = emptyList()
)

class LocationScreenViewModel(
    private val auth: FirebaseAuth = Firebase.auth,
    private val db: FirebaseFirestore = Firebase.firestore
) : ViewModel() {
    private val _state = MutableStateFlow(LocationState())
    val state = _state.asStateFlow()
    fun updateLocation(latitude: Double, longitude: Double, address: String) {
        val user = auth.currentUser
        user?.uid?.let {
            db.collection("users").document(it).set(
                hashMapOf(
                    "user" to user.uid,
                    "latitude" to latitude,
                    "longitude" to longitude,
                    "address" to address
                )
            )
        }
    }

    fun fetchDrivers() {
        db.collection("DRIVER").get().addOnSuccessListener { result ->
            val driverList = mutableListOf<Driver>()
            for (document in result) {
                val driver = document.toObject(Driver::class.java)
                driverList.add(driver)
            }
            _state.value = _state.value.copy(driverList = driverList)
        }
    }

    fun getStopList(selectDriver: Driver) {
        db.collection("DRIVER").document(selectDriver.user).get().addOnSuccessListener { result ->
            val driver = result.toObject(Driver::class.java)
            _state.value = _state.value.copy(selectedDriver = driver!!)
        }
    }
}