package com.example.lapakkita_android.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "store")
data class StoreEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "desc")
    val desc: String = "",

    @field:ColumnInfo(name = "photoUrl")
    val photoUrl: String,

    @field:ColumnInfo(name = "avgRating")
    val avgRating: Double,

    @field:ColumnInfo(name = "countRating")
    val countRating: Int = 0,

    @field:ColumnInfo(name = "lat")
    val lat: Double,

    @field:ColumnInfo(name = "lon")
    val lon: Double,

    @field:ColumnInfo(name = "isBookmarked")
    var isBookmarked: Boolean = false,

    @field:ColumnInfo(name = "category", defaultValue = "")
    var category : String = "",

    @field:ColumnInfo(name = "location", defaultValue = "")
    var location: String = "",
)
