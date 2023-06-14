package com.example.lapakkita_android.di

import android.content.Context
import com.example.lapakkita_android.data.local.database.LapakKitaDatabase
import com.example.lapakkita_android.data.local.repository.HistoryRepository
import com.example.lapakkita_android.data.local.repository.StoreRepository

object Injection {
    fun provideStoreRepository(context: Context): StoreRepository {
//        val apiService = ApiConfig().getApiService()
        val database = LapakKitaDatabase.getInstance(context)
        val dao = database.storeDao()
        return StoreRepository.getInstance(dao)
    }
    fun provideHistoryRepository(context: Context): HistoryRepository {
        val database = LapakKitaDatabase.getInstance(context)
        val dao = database.historyDao()
        return HistoryRepository.getInstance(dao)
    }
}