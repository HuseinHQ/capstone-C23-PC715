package com.example.lapakkita_android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lapakkita_android.data.local.entity.HistoryEntity

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY id DESC")
    suspend fun getHistory(): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistory(search: HistoryEntity)

    @Query("DELETE FROM history WHERE id = :id")
    suspend fun deleteHistory(id: Int)
}