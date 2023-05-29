package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.lapakkita_android.R

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
){
    var validPass by remember { mutableStateOf(false) }
    var validEmail by remember { mutableStateOf(false) }
    Column(modifier = modifier
        .fillMaxWidth()) {
        EmailForm(
            emailTextValidate = {
                validEmail = it
            }
        )
        Spacer(modifier = modifier.height(16.dp))
        PasswordForm(passTextValidate = {
            validPass = it
        })
        Spacer(modifier = modifier.height(16.dp))
        ButtonPrimary(
            onClicked = {},
            text = stringResource(R.string.login),
            isEnabled = validPass
        )
        Spacer(modifier = modifier.height(24.dp))
        ButtonSecondary(
            onClicked = {},
            text = stringResource(R.string.google_login),
            isEnabled = true
        )
    }
}