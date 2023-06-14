package com.example.lapakkita_android.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(

    @field:ColumnInfo(name = "id")
    @field:PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @field:ColumnInfo(name = "search")
    val search: String,
)
