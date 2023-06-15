package com.example.lapakkita_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lapakkita_android.data.local.entity.StoreEntity
import com.example.lapakkita_android.data.local.repository.StoreRepository
import com.example.lapakkita_android.data.local.Result
import com.example.lapakkita_android.data.local.entity.HistoryEntity
import com.example.lapakkita_android.data.local.repository.HistoryRepository
import com.example.lapakkita_android.ui.fragment.ExploreFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MapViewModel(
    private val storeRepository: StoreRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<Result<List<StoreEntity>>>(Result.Loading)
    val uiState: StateFlow<Result<List<StoreEntity>>> = _uiState.asStateFlow()

    private val _uiStateMarker = MutableStateFlow<Result<StoreEntity>>(Result.Loading)
    val uiStateMarker: StateFlow<Result<StoreEntity>> = _uiStateMarker.asStateFlow()

    fun getAllMarker(){
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

    fun onClickMarker(id: Int){
        viewModelScope.launch {
            _uiStateMarker.value = Result.Loading
            storeRepository
                .searchStoreById(id)
                .catch {
                _uiStateMarker.value = Result.Error("Error")
                }
                .collect{
                    _uiStateMarker.value = Result.Success(it)
                }
        }
    }
}