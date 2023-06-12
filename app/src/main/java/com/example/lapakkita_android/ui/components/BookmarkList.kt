package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lapakkita_android.R
import com.example.lapakkita_android.data.local.entity.StoreEntity

@Composable
fun BookmarkList(
    listItem: List<StoreEntity>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.bookmark),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.inter_bold))
        )

        if(listItem.isNotEmpty()) {
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
                        onClick = { onClick() }
                    )
                }
            }
        }
    }
    if(listItem.isEmpty()) {
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