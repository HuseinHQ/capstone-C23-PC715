package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lapakkita_android.R
import com.example.lapakkita_android.data.local.entity.StoreEntity
import com.example.lapakkita_android.data.local.Result
import com.example.lapakkita_android.ui.viewmodel.BookmarkViewModel

@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.bookmark),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.inter_bold))
        )
        Box(modifier = Modifier){
            viewModel.uiState.collectAsState(initial = Result.Loading).value.let { state ->
                when (state) {
                    is Result.Loading -> {
                        viewModel.getBookmarkedStore()
                        LoadingScreen()
                    }
                    is Result.Success -> {
                        if(state.data.isNotEmpty()) {
                            BookmarkList(
                                listItem = state.data,
                                onClick = onClick,
                                viewModel = viewModel
                            )
                        }
                        else{
                            ErrorScreen(message = stringResource(R.string.no_data))
                        }
                    }
                    is Result.Error -> {
                        ErrorScreen(message = stringResource(R.string.connection_error))
                    }
                }
            }
        }
    }
}

@Composable
fun BookmarkList(
    listItem: List<StoreEntity>,
    onClick: (Int) -> Unit,
    viewModel: BookmarkViewModel,
    modifier: Modifier = Modifier
) {
        if (listItem.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
                    .fillMaxHeight()
                    .padding(top = 8.dp)
            ) {
                items(
                    listItem,
//            key = { it.id }
                ) { data ->
                    BookmarkCard(
                        item = data,
                        onClick = { onClick(data.id) },
                        deleteBookmark = {
                            viewModel.setBookmark(data, false)
                            viewModel.getBookmarkedStore()
                        }
                    )
                }
            }
        }
    if (listItem.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No Data",
            )
        }
    }
}