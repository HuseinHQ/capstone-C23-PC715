package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.lapakkita_android.R

@Composable
fun ButtonContainer(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    Box(
        modifier = modifier
            .width(80.dp)
            .background(color = MaterialTheme.colors.surface)
            .shadow(8.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        ButtonPrimary(
            onClicked = {},
            text = stringResource(R.string.find_location),
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
    }
}