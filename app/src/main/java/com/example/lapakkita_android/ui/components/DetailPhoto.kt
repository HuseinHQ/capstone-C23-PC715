package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun DetailPhoto(
    modifier: Modifier = Modifier,
    imageResource: Int
) {
    Image(
        painter = painterResource(imageResource),
        contentDescription = null,
        modifier = modifier
            .height(380.dp)
            .shadow(8.dp)
            .clip(
                RoundedCornerShape(
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp,
                )
            ),
        contentScale = ContentScale.Crop
    )
}