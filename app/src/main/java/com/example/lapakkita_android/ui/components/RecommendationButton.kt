package com.example.lapakkita_android.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.lapakkita_android.R

@Composable
fun RecommendationButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.secondary),
        ),
        elevation = ButtonDefaults.elevation(0.dp),
        contentPadding = PaddingValues(16.dp),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            content = {
                Text(
                    text = stringResource(R.string.find_recommendation),
                    color = colorResource(R.color.primary),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                    letterSpacing = 0.em,
                    modifier = Modifier
                        .weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = colorResource(R.color.primary)
                )
            }
        )
    }
}