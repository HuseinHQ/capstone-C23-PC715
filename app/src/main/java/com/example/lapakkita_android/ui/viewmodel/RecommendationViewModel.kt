package com.example.lapakkita_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lapakkita_android.data.local.entity.StoreEntity
import com.example.lapakkita_android.data.local.repository.StoreRepository
import com.example.lapakkita_android.data.local.Result
import com.example.lapakkita_android.ui.activity.RecommendationActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class RecommendationViewModel(
    private val repository: StoreRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<Result<List<StoreEntity>>>(Result.Loading)
    val uiState: StateFlow<Result<List<StoreEntity>>> = _uiState.asStateFlow()

    fun getRecommendation(category: String){
        viewModelScope.launch {
            repository
                .searchStoreByCategoryAndName(
                    name = "",
                    category = category
                )
                .catch {
                    _uiState.value = Result.Error("Error")
                }
                .collect{item ->
                    if(item.isNotEmpty()){
                        _uiState.value = Result.Success(item)
                    }
                    else{
                        _uiState.value = Result.Error(RecommendationActivity.NOT_FOUND)
                    }
                }
        }
    }
}