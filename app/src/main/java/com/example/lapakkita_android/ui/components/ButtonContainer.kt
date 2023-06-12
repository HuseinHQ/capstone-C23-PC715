package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.lapakkita_android.R

@Composable
fun ButtonContainer(
    onClicked: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(4.dp)
        ){
            Canvas(modifier = Modifier.fillMaxSize()){
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.1f))
                    ),
                    size = Size(size.width, size.height)
                )
            }
        }
        Box(
            modifier = modifier
                .height(76.dp)
                .background(color = MaterialTheme.colors.surface),
            contentAlignment = Alignment.Center
        ){
            ButtonPrimary(
                onClicked = {onClicked()},
                text = text,
            )
        }
    }
}