package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.lapakkita_android.R

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier
){
    TopAppBar(
        backgroundColor = colorResource(R.color.primary),
        elevation = AppBarDefaults.TopAppBarElevation,
        modifier = modifier.fillMaxWidth(),
        title = {
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily(Font(R.font.inter_bold)),
                modifier = modifier
                    .fillMaxWidth()
            )
        }
    )
}