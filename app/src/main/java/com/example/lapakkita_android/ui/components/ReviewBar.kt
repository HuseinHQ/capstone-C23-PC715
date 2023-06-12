package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.lapakkita_android.R

@Composable
fun ReviewBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(1.dp)
        ){
            Canvas(modifier = Modifier.fillMaxSize()){
                drawRect(
                    color = Color.Black.copy(alpha = 0.2f),
                    size = Size(size.width, size.height)
                )
            }
        }
        Button(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.surface,
            ),
            elevation = ButtonDefaults.elevation(0.dp),
            contentPadding = PaddingValues(0.dp),
            shape = RectangleShape,
        ){
            Row(
                modifier = modifier
                    .height(72.dp)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.review),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.em,
                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                    modifier = Modifier
                        .weight(1f)
                )
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_forward_24),
                    contentDescription = stringResource(R.string.find_review)
                )
            }
        }
        Box(
            modifier = Modifier
                .height(1.dp)
        ){
            Canvas(modifier = Modifier.fillMaxSize()){
                drawRect(
                    color = Color.Black.copy(alpha = 0.2f),
                    size = Size(size.width, size.height)
                )
            }
        }
    }
}