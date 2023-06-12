package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lapakkita_android.R
import com.example.lapakkita_android.data.local.entity.HistoryEntity

@Composable
fun HistoryList(
    listItem: List<HistoryEntity>,
    onItemClick: (String) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ){
        items(
            listItem,
//            key = { it.id }
        ) { data ->
            HistoryCard(
                item = data.search,
                onItemClick = { onItemClick(data.search) },
                onDeleteClick = { onDeleteClick() }
            )
        }
    }
}

@Composable
fun HistoryCard(
    item: String,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .clickable { onItemClick()  }
    ) {
       Row(
           modifier = Modifier
               .padding(horizontal = 8.dp),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.Center
       ) {
           Icon(
               painter = painterResource(R.drawable.baseline_history_24),
               contentDescription = null
           )
           Text(
               text = item,
               fontFamily = FontFamily(Font(R.font.inter_medium)),
               fontSize = 14.sp,
               modifier = Modifier
                   .weight(1f)
                   .padding(start = 20.dp)
           )
           IconButton(
               onClick = {  }
           ) {
               Icon(
                   imageVector = Icons.Default.Close,
                   contentDescription = stringResource(R.string.delete)
               )
           }
       }
    }
}