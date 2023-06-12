package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.lapakkita_android.R
import com.example.lapakkita_android.data.local.entity.StoreEntity

@Composable
fun RecommendationList(
    listItem: List<StoreEntity>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxHeight()
    ){
        items(
            listItem,
//            key = { it.id }
        ) { data ->
            StoreCard(
                item = data,
                onClick = onClick
            )
        }
    }
}