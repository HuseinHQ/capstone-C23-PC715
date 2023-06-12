package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.lapakkita_android.R

@Composable
fun BackContainer(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    IconButton(
        onClick = { onClick() },
        modifier = modifier
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.3f))
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(R.string.back),
            tint = Color.White
        )
    }
}