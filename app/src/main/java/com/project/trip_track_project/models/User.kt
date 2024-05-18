package com.project.trip_track_project.models


enum class Action{
    IDLE,
    WALKING,
    DRIVING,
    SEARCHING,
}
data class User(
    val uid: Int = 0,
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val role: String = "user",
    val createdAt: String = "",
    val updatedAt: String = "",
    val lastLogin: String = System.currentTimeMillis().toString(),
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val isVerified: Boolean = true,
    val userStatus: Action = Action.IDLE
)