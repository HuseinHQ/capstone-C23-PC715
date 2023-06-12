package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.lapakkita_android.R

@Composable
fun ProfileCard(
    editPhotoClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(start = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(140.dp),
            contentAlignment = Alignment.TopCenter
        ){
            AsyncImage(
                model = "https://i.pravatar.cc/300",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.placeholder_image),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
            IconButton(
                onClick = { editPhotoClick() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .width(32.dp)
                    .height(32.dp)
                    .background(colorResource(R.color.primary))
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.change_profile_photo),
                    tint = Color.White,
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
            }
        }
        Text(
            text = "John Doe",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.inter_bold)),
            modifier = Modifier
                .padding(top = 8.dp)
        )
        Text(
            text = "johndoe@email.com",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.inter_medium)),
            modifier = Modifier
                .padding(top = 4.dp)
        )
    }
}