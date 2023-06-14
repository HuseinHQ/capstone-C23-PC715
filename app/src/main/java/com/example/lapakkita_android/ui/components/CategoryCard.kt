package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.lapakkita_android.R

@Composable
fun CategoryCard(
    item: String,
    itemClicked: (String) -> Unit,
    selectedCategory: String,
    modifier: Modifier = Modifier
){
    Button(
        onClick = {
          itemClicked(item)
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor =  if (item == selectedCategory) colorResource(R.color.primary)
            else colorResource(R.color.secondary),
            contentColor = if(item == selectedCategory) Color.White
            else colorResource(R.color.primary),
        ),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = item,
            fontFamily = FontFamily(Font(R.font.inter_medium)),
            fontSize = 14.sp,
            letterSpacing = 0.em,
            textAlign = TextAlign.Center,
            modifier = modifier
                .background(Color.Transparent)
                .fillMaxWidth()
        )
    }
}