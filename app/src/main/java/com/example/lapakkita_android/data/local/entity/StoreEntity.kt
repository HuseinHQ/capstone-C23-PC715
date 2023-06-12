package com.example.lapakkita_android.data.local.entity

data class StoreEntity(
    val id: Int,
    val name: String,
    val photoUrl: String,
    val rating: Double,
    val lat: Double,
    val lon: Double,
    var location: String = ""
)
