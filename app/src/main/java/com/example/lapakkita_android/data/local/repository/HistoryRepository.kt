package com.example.lapakkita_android.data.local.repository

import com.example.lapakkita_android.data.local.entity.HistoryEntity
import com.example.lapakkita_android.data.local.dao.HistoryDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class HistoryRepository private constructor(
 private val historyDao: HistoryDao
){
    suspend fun getHistoryList() : Flow<List<HistoryEntity>> = flowOf(historyDao.getHistory())

    suspend fun addHistory(search: String){

        if(search!="") {
            historyDao.getHistory().forEach { history ->
                if (history.search == search) {
                    historyDao.deleteHistory(history.id)
                }
            }

            historyDao.insertHistory(
                HistoryEntity(
                    search = search
                )
            )
        }
    }

    suspend fun deleteHistory(id: Int){
        historyDao.deleteHistory(id)
    }
    companion object {
        @Volatile
        private var instance: HistoryRepository? = null
        fun getInstance(
//            apiService: ApiService,
            historyDao: HistoryDao
        ): HistoryRepository =
            instance ?: synchronized(this) {
                instance ?: HistoryRepository(
//                    apiService,
                    historyDao,
                )
            }.also { instance = it }
    }
}