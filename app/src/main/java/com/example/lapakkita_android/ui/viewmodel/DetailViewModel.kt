package com.example.lapakkita_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lapakkita_android.data.local.entity.StoreEntity
import com.example.lapakkita_android.data.local.repository.StoreRepository
import com.example.lapakkita_android.data.local.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: StoreRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<Result<StoreEntity>>(Result.Loading)
    val uiState: StateFlow<Result<StoreEntity>> = _uiState.asStateFlow()

    fun getDetailStore(id: Int){
        viewModelScope.launch {
            repository
                .getDetail(id)
                .catch {
                    _uiState.value = Result.Error("Error")
                }
                .collect{
                    if(it!=null){
                        _uiState.value = Result.Success(it)
                    }
                    else{
                        _uiState.value = Result.Error("Error")
                    }
                }
        }
    }
}