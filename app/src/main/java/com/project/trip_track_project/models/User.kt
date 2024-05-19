package com.project.trip_track_project.models


enum class Action {
    IDLE,
    WALKING,
    DRIVING,
    SEARCHING,
}

data class User(
    val uid: Int = 0,
    val firstname: String = "",
    val lastname: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val createdAt: String = "",
    val lastLogin: String = System.currentTimeMillis().toString(),
    // location
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val userStatus: Action = Action.IDLE,
    val city: String = "",
    val country: String = "",
    val knownName: String = "",
    val postalCode: String = "",
    val state: String = "",

    )

data class Driver(
    val user: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val stops: List<StopLocation> = emptyList(),
    val address: String = "",
    val homeAddress: String = "",
    val isDriving: Boolean = false,
    val vehicleType: String = "Taxi",
    val phoneNumber: String = "",
    val aadhaarCardNumber: String = "",
    val name: String = "",
    val email: String = "",
)

data class StopLocation(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = "",
)

enum class VehicleChoices {
    TAXI, E_RICKSHAW, BUS
}



