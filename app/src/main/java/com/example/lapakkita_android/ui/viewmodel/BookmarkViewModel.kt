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

class BookmarkViewModel(
    private val repository: StoreRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<Result<List<StoreEntity>>>(Result.Loading)
    val uiState: StateFlow<Result<List<StoreEntity>>> = _uiState.asStateFlow()

    fun getBookmarkedStore() {
        viewModelScope.launch {
            repository
                .getBookmarkList()
                .catch {
                    _uiState.value = Result.Error("Error")
                }
                .collect {
                    _uiState.value = Result.Success(it)
                }
        }
    }

    fun setBookmark(item: StoreEntity, added: Boolean, location: String = ""){
        viewModelScope.launch {
            repository.setBookmarkItem(item, added, location)
        }
    }
}