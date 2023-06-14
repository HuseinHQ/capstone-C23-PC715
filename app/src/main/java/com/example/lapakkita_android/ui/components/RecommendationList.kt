package com.example.lapakkita_android.ui.components

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lapakkita_android.data.local.entity.StoreEntity
import com.example.lapakkita_android.R

@Composable
fun RecommendationList(
    selectedCategory: String,
    listItem: List<StoreEntity>,
    onClick: (Int) -> Unit,
    context: Context,
    modifier: Modifier = Modifier
){
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.fillMaxHeight()
        ){
            item{
                Text(
                    text = stringResource(R.string.selected_category, selectedCategory),
                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            items(
                listItem,
//            key = { it.id }
            ) { data ->
                RecommendationCard(
                    item = data,
                    context = context,
                    onClick = {
                        onClick(it)
                    }
                )
            }
        }
}