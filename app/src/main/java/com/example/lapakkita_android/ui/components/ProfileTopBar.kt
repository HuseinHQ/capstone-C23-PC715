package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lapakkita_android.R

@Composable
fun ProfileTopBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        backgroundColor = colorResource(R.color.primary),
        elevation = AppBarDefaults.TopAppBarElevation,
        modifier = modifier
            .fillMaxWidth(),
    ){
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.lorem),
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily(Font(R.font.inter_bold)),
                modifier = modifier
                    .weight(1f)
            )
            IconButton(onClick = { onClick() }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_logout_24),
                    contentDescription = stringResource(R.string.logout),
                    tint = Color.White
                )
            }
        }
    }
}