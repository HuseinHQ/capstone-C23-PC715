package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.lapakkita_android.data.local.entity.StoreEntity
import com.example.lapakkita_android.R

@Composable
fun GridListStore(
    modifier: Modifier = Modifier,
    listItem: List<StoreEntity>,
    toDetail: (Int) -> Unit,
){
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxHeight()
    ) {
        items(
            listItem,
//            key = { it.id }
        ) { data ->
            StoreCard(
                item = data,
                toDetail = toDetail
            )
        }
    }
}

@Composable
fun StoreCard(
    item: StoreEntity,
    toDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .height(232.dp)
            .clickable {
                toDetail(item.id)
            },
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
        ) {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.BottomEnd
            ){
                AsyncImage(
                    model = item.photoUrl,
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.placeholder_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Row(
                    modifier = Modifier
                        .padding(
                            12.dp
                        )
                        .width(54.dp)
                        .height(22.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.4f)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(R.drawable.baseline_star_border_24),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .height(16.dp)
                    )
                    Text(
                        text = "3.5",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.inter_bold)),
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = item.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = item.location,
                    fontFamily = FontFamily(Font(R.font.inter_regular)),
                    fontSize = 14.sp,
                )
            }
        }
    }
}