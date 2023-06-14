package com.example.lapakkita_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lapakkita_android.data.local.entity.StoreEntity
import com.example.lapakkita_android.data.local.repository.StoreRepository
import com.example.lapakkita_android.data.local.Result
import com.example.lapakkita_android.data.local.entity.HistoryEntity
import com.example.lapakkita_android.data.local.repository.HistoryRepository
import com.example.lapakkita_android.ui.fragment.ExploreFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val storeRepository: StoreRepository,
    private val historyRepository: HistoryRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<Result<List<StoreEntity>>>(Result.Loading)
    val uiState: StateFlow<Result<List<StoreEntity>>> = _uiState.asStateFlow()

    private val _uiStateHistory = MutableStateFlow<Result<List<HistoryEntity>>>(Result.Loading)
    val uiStateHistory: StateFlow<Result<List<HistoryEntity>>> = _uiStateHistory.asStateFlow()

    fun getListStore(){
        viewModelScope.launch {
            _uiState.value = Result.Loading
            storeRepository
                .getStoreList()
                .catch {
                    _uiState.value = Result.Error("Error")
                }
                .collect{
                    if(it.isNotEmpty()){
                        _uiState.value = Result.Success(it)
                    }
                    else{
                        _uiState.value = Result.Error("Error")
                    }
                }
        }
    }

    fun searchStore(name: String){
        viewModelScope.launch {
            _uiState.value = Result.Loading
            storeRepository
                .searchStore(name)
                .collect { item ->
                    if(item.isNotEmpty()) {
                        _uiState.value = Result.Success(item)
                    }
                    else{
                        _uiState.value = Result.Error(ExploreFragment.NOT_FOUND)
                    }
                }
        }
    }
    fun searchStoreByCategoryAndName(category: String, name: String){
        viewModelScope.launch {
            _uiState.value = Result.Loading
            storeRepository
                .searchStoreByCategoryAndName(name, category)
                .collect { item ->
                    if(item.isNotEmpty()) {
                        _uiState.value = Result.Success(item)
                    }
                    else{
                        _uiState.value = Result.Error(ExploreFragment.NOT_FOUND)
                    }
                }
        }
    }

    fun getHistoryList(){
        viewModelScope.launch {
            historyRepository
                .getHistoryList()
                .collect { item ->
                    if(item.isNotEmpty()) {
                        _uiStateHistory.value = Result.Success(item)
                    }
                }
        }
    }

    fun addHistory(query: String){
        viewModelScope.launch {
            historyRepository
                .addHistory(
                    search = query
                )
        }
    }
    fun deleteHistory(id: Int){
        viewModelScope.launch {
            historyRepository
                .deleteHistory(id)
        }
    }
}