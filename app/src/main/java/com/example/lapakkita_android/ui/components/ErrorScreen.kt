package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lapakkita_android.R

@Composable
fun ErrorScreen(
    message: String,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier
        .fillMaxSize()
        .wrapContentHeight()
    ){
//        Icon(
//            imageVector = Icons.Default.Warning,
//            contentDescription = stringResource(id = R.string.connection_error),
//            tint = Color(0xFF707070),
//            modifier = modifier
//                .height(24.dp)
//                .align(Alignment.CenterHorizontally)
//        )
        Text(
            text = message,
            fontFamily = FontFamily(Font(R.font.inter_medium)),
            textAlign = TextAlign.Center,
            color = Color(0xFF707070),
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
    }
}
