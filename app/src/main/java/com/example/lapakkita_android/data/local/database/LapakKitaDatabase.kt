package com.example.lapakkita_android.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lapakkita_android.data.local.entity.StoreEntity
import com.example.lapakkita_android.data.local.dao.HistoryDao
import com.example.lapakkita_android.data.local.dao.StoreDao
import com.example.lapakkita_android.data.local.entity.HistoryEntity

@Database(
    entities = [StoreEntity::class, HistoryEntity::class],
    version = 1,
//    autoMigrations = [
//        AutoMigration(from = 1, to = 2),
//        AutoMigration(from = 2, to = 3),
//    ],
    exportSchema = true)
abstract class LapakKitaDatabase : RoomDatabase() {
    abstract fun storeDao(): StoreDao
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var instance: LapakKitaDatabase? = null
        fun getInstance(context: Context): LapakKitaDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    LapakKitaDatabase::class.java, "LapakKita.db",
                ).build()
            }
    }
}