package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.example.lapakkita_android.R

@Composable
fun ReviewTextField(
    getCommentText: (String) -> Unit,
    modifier: Modifier = Modifier
){
    var text by remember { mutableStateOf(TextFieldValue()) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxSize(),
        value = text,
        onValueChange = { newValue ->
            text = newValue
            getCommentText(newValue.text)
        },
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFFEFEFEF),
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = colorResource(R.color.primary),
            unfocusedIndicatorColor = Color.Transparent,
            placeholderColor = Color(0xFF707070),
            textColor = Color.Black,
            cursorColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text,
        ),
    )
}