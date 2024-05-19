package com.project.trip_track_project.screens.location

import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.project.trip_track_project.models.Driver
import com.project.trip_track_project.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class LoadingStatus {
    LOADING,
    LOADED,
    ERROR,
}

data class LocationState(
    val driverList: List<Driver> = emptyList(),
    val selectedDriver: Driver = Driver(),
    val user: User = User(),
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val userAddress: String = "",
    val currentLocation: String = "",
    val loadingStatus: LoadingStatus = LoadingStatus.LOADING,
    val permissionRequestState:Int = 1,
    val showMap: Boolean = false,
    val showRequestPermissionButton: Boolean = false,
    val lastLocUpdate: Long = 1000,
)

sealed class LocationEvent {
    data class OnDriverSelected(val driver: Driver) : LocationEvent()
    data class OnPermissionRequest(val permissionRequestState: Int) : LocationEvent()
    data class showMap(val showMap: Boolean) : LocationEvent()
    data class OnLocationUpdate(val latitude: Double, val longitude: Double) : LocationEvent()
    data class showRequestPermissionButton(val showRequestPermissionButton: Boolean) : LocationEvent()
}

@Suppress("DEPRECATION")
class LocationViewModel(
    val geocoder: Geocoder,
    val db: FirebaseFirestore = Firebase.firestore,
    val auth: FirebaseAuth = Firebase.auth,
):ViewModel() {
    private val _state = MutableStateFlow(LocationState())
    val state: StateFlow<LocationState> = _state.asStateFlow()

    init {
        getAvailableDrivers()
    }

    private fun getAvailableDrivers() {
        db.collection("DRIVER").get().addOnSuccessListener { result ->
            val driverList = mutableListOf<Driver>()
            for (document in result) {
                val driver = document.toObject(Driver::class.java)
                driverList.add(driver)
            }
            _state.update { it.copy(driverList = driverList, loadingStatus = LoadingStatus.LOADED) }

        }.addOnFailureListener { exception ->
            Log.w("LocationViewModel", "Error getting documents.", exception)
            _state.update { it.copy(loadingStatus = LoadingStatus.ERROR) }
        }
    }

    private fun updateUserLocation(latitude: Double, longitude: Double) {
        val auth = Firebase.auth
        val db = Firebase.firestore
        val address: Address?
        Log.d("LocationViewModel", "latitude: $latitude, longitude: $longitude")
        val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                address = addresses[0]
                val city = address.locality;
                val state = address.adminArea;
                val country = address.countryName;
                val postalCode = address.postalCode;
                val knownName = address.featureName; // Only if available else return NULL
                val user = auth.currentUser
                var cleanAddr = ""
                for (lines in address.getAddressLine(0)) {
                    cleanAddr += lines
                }
                user?.uid?.let {
                    db.collection("users").document(it).update(
                        mapOf(
                            "latitude" to latitude,
                            "longitude" to longitude,
                            "city" to city,
                            "state" to state,
                            "country" to country,
                            "postalCode" to postalCode,
                            "knownName" to knownName,
                            "address" to cleanAddr
                        )
                    ).addOnSuccessListener {
                        Log.d("LocationViewModel", "User location successfully written!")
                    }.addOnFailureListener { e ->
                        Log.w("LocationViewModel", "Error writing document", e)
                    }
                }

            } else {
                Log.d("LocationViewModel", "No address found")
            }
        }
    }

    class Factory(private val geocoder: Geocoder) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LocationViewModel(geocoder) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}