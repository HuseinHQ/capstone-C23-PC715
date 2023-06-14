package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.lapakkita_android.R

@Composable
fun ButtonPrimary(
    modifier: Modifier = Modifier,
    onClicked : () -> Unit,
    text: String,
){
    Button(
        onClick = { onClicked() },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.primary),
            contentColor = Color.White,
        ),
        elevation = ButtonDefaults.elevation(0.dp),
        shape = RoundedCornerShape(50),
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.inter_bold)),
            letterSpacing = 0.em,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
fun ButtonSecondary(
    modifier: Modifier = Modifier,
    onClicked : () -> Unit,
    text: String,
){
    Button(
        onClick = { onClicked() },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.secondary),
            contentColor = colorResource(R.color.primary)
        ),
        elevation = ButtonDefaults.elevation(0.dp),
        shape = RoundedCornerShape(50),
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.inter_bold)),
            letterSpacing = 0.em,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
@Preview
@Composable
fun SimpleComposablePreview() {
    ButtonSecondary(
        onClicked = {},
        text = stringResource(R.string.register),
    )
}