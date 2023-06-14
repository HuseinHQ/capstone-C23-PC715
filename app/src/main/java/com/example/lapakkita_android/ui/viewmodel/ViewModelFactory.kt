package com.example.lapakkita_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lapakkita_android.data.local.repository.HistoryRepository
import com.example.lapakkita_android.data.local.repository.StoreRepository

class ViewModelFactory(
    private val storeRepository: StoreRepository,
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(storeRepository) as T
        }
        else if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            return BookmarkViewModel(storeRepository) as T
        }
        else if (modelClass.isAssignableFrom(RecommendationViewModel::class.java)) {
            return RecommendationViewModel(storeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}