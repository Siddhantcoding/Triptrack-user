package com.project.trip_track_project.screens.Vehicle

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.project.trip_track_project.models.Driver
import com.project.trip_track_project.models.VehicleChoices
import com.project.trip_track_project.screens.location.LoadingStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class VehicleState(
    val vehicleType: VehicleChoices = VehicleChoices.TAXI,
    val driverList: List<Driver> = emptyList(),
    val selectedDriver: Driver = Driver(),
    val showDriverBottomSheet: Boolean = false,
    val loadingStatus: LoadingStatus = LoadingStatus.LOADING,
)

sealed class VehicleEvent {
    data class OnDriverSelected(val driver: Driver) : VehicleEvent()
    data object OnDriverUnSelected : VehicleEvent()
}

class VehicleViewModel(
    val vehicleType: VehicleChoices = VehicleChoices.TAXI,
    val db: FirebaseFirestore = Firebase.firestore,
    val auth: FirebaseAuth = Firebase.auth,
) : ViewModel() {
    private val _state = MutableStateFlow(VehicleState())
    val state: StateFlow<VehicleState> = _state.asStateFlow()

    init {
        _state.update { it.copy(vehicleType = vehicleType) }
        getAvailableDrivers()
    }

    private fun getAvailableDrivers() {
        _state.update { it.copy(loadingStatus = LoadingStatus.LOADING) }
        db.collection("DRIVER").get().addOnSuccessListener { result ->
            val driverList = mutableListOf<Driver>()
            for (document in result) {
                val driver = document.toObject(Driver::class.java)
                if (driver.vehicleType == vehicleType.toString()) {
                    driverList.add(driver)
                }
            }
            _state.update { it.copy(driverList = driverList, loadingStatus = LoadingStatus.LOADED) }

        }.addOnFailureListener { exception ->
            Log.w("LocationViewModel", "Error getting documents.", exception)
            _state.update { it.copy(loadingStatus = LoadingStatus.ERROR) }
        }
    }

    fun onEvent(event: VehicleEvent) {
        when (event) {
            is VehicleEvent.OnDriverSelected -> {
                _state.update {
                    it.copy(
                        selectedDriver = event.driver,
                        showDriverBottomSheet = true
                    )
                }
            }

            is VehicleEvent.OnDriverUnSelected -> {
                _state.update {
                    it.copy(
                        selectedDriver = Driver(),
                        showDriverBottomSheet = false
                    )
                }
            }
        }
    }


    class Factory(
        private val vehicleType: VehicleChoices,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VehicleViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return VehicleViewModel(vehicleType = vehicleType) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}