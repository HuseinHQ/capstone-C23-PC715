package com.example.lapakkita_android.data.local.entity

data class ReviewEntity(
    val id: Int,
    val user: String,
    val photoUrl: String,
    val rating: Double,
    val description: String
)
