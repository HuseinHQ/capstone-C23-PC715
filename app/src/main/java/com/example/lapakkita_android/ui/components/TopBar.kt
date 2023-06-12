package com.example.lapakkita_android.ui.components

import android.annotation.SuppressLint
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TopBar(
    text: String,
    toBack: () -> Unit,
    modifier: Modifier = Modifier,
){
    TopAppBar(
        backgroundColor = colorResource(R.color.primary),
        elevation = AppBarDefaults.TopAppBarElevation,
        modifier = modifier.fillMaxWidth()
    ){
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.CenterStart
        ){
            IconButton(onClick = { toBack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = Color.White
                )
            }
            Text(
                text = text,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily(Font(R.font.inter_bold)),
                modifier = modifier
                    .fillMaxWidth()
            )
        }
    }
}