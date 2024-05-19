package com.project.trip_track_project.screens.driver

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.project.trip_track_project.models.Driver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

//data class NearbyDriverState(
//    val driverList: List<Driver> = emptyList(),
//    val selectedDriver: Driver = Driver(),
//    val selectVehicle: VEHICLE_CHOICES = VEHICLE_CHOICES.ALL
//)
//
//sealed class NearbyDriverEvent {
//    data class SelectDriver(val driver: Driver) : NearbyDriverEvent()
//}
//
//class NearbyDriverViewModel(
//    val auth: FirebaseAuth = Firebase.auth,
//    val db: FirebaseFirestore = Firebase.firestore
//) : ViewModel() {
//    private val _state: MutableStateFlow<NearbyDriverState> = MutableStateFlow(NearbyDriverState())
//    val state = _state.asStateFlow()
//
//    fun getDriverList() {
//        val driverList = mutableListOf<Driver>()
//        db.collection("DRIVER").get().addOnSuccessListener { result ->
//            for (document in result) {
//                val driver = document.toObject(Driver::class.java)
//                driverList.add(driver)
//            }
//            _state.value = _state.value.copy(driverList = driverList)
//        }
//    }
//
//    fun getDriver(driverId: String) {
//        db.collection("DRIVER").document(driverId).get().addOnSuccessListener { document ->
//            val driver = document.toObject(Driver::class.java)
//            driver?.let {
//                _state.value = _state.value.copy(selectedDriver = driver)
//            }
//        }
//    }
//}