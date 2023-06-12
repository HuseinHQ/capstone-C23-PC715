package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.lapakkita_android.R
import com.example.lapakkita_android.data.local.entity.StoreEntity

@Composable
fun BookmarkCard(
    item: StoreEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp)
    ){
        Row(
            modifier = modifier
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ){
            AsyncImage(
                model = item.photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.placeholder_image),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(96.dp)
                    .height(108.dp)
                    .padding(top = 4.dp)
            )
            Column(
                modifier = modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = item.name,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                )
                Row(
                    modifier = Modifier
                        .padding(top = 4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_star_border_24),
                        contentDescription = null,
                        modifier = Modifier
                            .height(18.dp)
                    )
                    Text(
                        text = item.rating.toString(),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.inter_medium)),
                    )
                }
                Text(
                    text = item.location,
                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                    fontSize = 14.sp,
                    modifier = modifier
                        .padding(top = 8.dp)
                )
            }
            Box(
                modifier = Modifier
                    .height(108.dp),
                contentAlignment = Alignment.Center
            ){
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.remove_bookmark)
                    )
                }
            }
        }
    }
}