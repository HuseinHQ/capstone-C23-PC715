package com.example.lapakkita_android.data.local.dao

import androidx.room.*
import com.example.lapakkita_android.data.local.entity.StoreEntity

@Dao
interface StoreDao {
    @Query("SELECT * FROM store ORDER BY name ASC")
    suspend fun getStore(): List<StoreEntity>

//    @Query("SELECT * FROM anime where favorite = 1")
//    fun getBookmarkedNews(): Flow<List<AnimeEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStore(store: List<StoreEntity>)

    @Update
    suspend fun updateStore(item: StoreEntity)

    @Query("DELETE FROM store WHERE isBookmarked = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM store WHERE id = :id AND isBookmarked = 1)")
    suspend fun isFavorite(id: Int): Boolean
}