package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.lapakkita_android.R

@Composable
fun RegisterForm(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        var validUser by remember { mutableStateOf(false) }
        var validEmail by remember { mutableStateOf(false) }
        var validPass by remember { mutableStateOf(false) }
        var validRepass by remember { mutableStateOf(false) }
        UsernameForm()
        Spacer(modifier = Modifier.height(16.dp))
        EmailForm(
            emailTextValidate = {
                validEmail = it
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordForm(passTextValidate = {
            validPass = it
        })
        Spacer(modifier = Modifier.height(16.dp))
        ButtonPrimary(
            onClicked = {},
            text = stringResource(R.string.register),
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