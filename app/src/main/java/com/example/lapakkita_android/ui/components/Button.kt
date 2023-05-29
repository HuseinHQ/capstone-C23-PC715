package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lapakkita_android.R

@Composable
fun ButtonPrimary(
    modifier: Modifier = Modifier,
    onClicked : () -> Unit,
    text: String,
    isEnabled: Boolean
){
    Button(
        onClick = { onClicked() },
        modifier = modifier
            .fillMaxWidth(),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.green_500),
            contentColor = Color.White,
        ),
        shape = RoundedCornerShape(50),
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.inter_medium)),
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
fun ButtonSecondary(
    modifier: Modifier = Modifier,
    onClicked : () -> Unit,
    text: String,
    isEnabled: Boolean
){
    Button(
        onClick = { onClicked() },
        modifier = modifier
            .fillMaxWidth(),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0XFFD5ECD7),
            contentColor = Color(0XFF4EB156)
        ),
        elevation = ButtonDefaults.elevation(0.dp),
        shape = RoundedCornerShape(50),
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.inter_medium)),
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
        isEnabled = true
    )
}