package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.lapakkita_android.R

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
){
    Box(modifier = modifier.fillMaxSize()){
        CircularProgressIndicator(
            color = colorResource(R.color.primary),
            modifier = modifier
                .height(8.dp)
                .align(alignment = Center)
        )
    }
}