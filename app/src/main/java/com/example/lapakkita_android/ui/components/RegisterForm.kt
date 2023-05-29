package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lapakkita_android.R

@Composable
fun RegisterForm(
    loginIntent: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        var isErrorRegister by remember { mutableStateOf(false) }
        var isClickedButton by remember { mutableStateOf(false) }

        var validUser by remember { mutableStateOf(false) }
        var validEmail by remember { mutableStateOf(false) }
        var validPass by remember { mutableStateOf(false) }
        var validRepass by remember { mutableStateOf(false) }

        var pass by remember { mutableStateOf("") }
        var confirmPass by remember { mutableStateOf("") }
        UsernameForm(
            userTextValidate = {
                validUser = it
                if(isClickedButton)
                    isErrorRegister = !(validEmail && validUser && validPass && validRepass)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        EmailForm(
            emailTextValidate = {
                validEmail = it
                if(isClickedButton)
                    isErrorRegister = !(validEmail && validUser && validPass && validRepass)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordForm(
            passTextValidate = {
                validPass = it
                if(isClickedButton)
                    isErrorRegister = !(validEmail && validUser && validPass && validRepass)
            },
            passText = {
                pass = it
            },
            rePassTextValidate = {
                validRepass = it
                if(isClickedButton)
                    isErrorRegister = !(validEmail && validUser && validPass && validRepass)
            },
            rePassText = confirmPass
        )
        Spacer(modifier = Modifier.height(16.dp))
        RePasswordForm(
            rePassTextValidate = {
                validRepass = it
                if(isClickedButton)
                    isErrorRegister = !(validEmail && validUser && validPass && validRepass)
            },
            confirmPass = {
                confirmPass = it
            },
            passText = pass
        )
        Spacer(modifier = Modifier.height(16.dp))
        if(isErrorRegister) {
            Text(
                text =
                if (!validUser) {
                    stringResource(id = R.string.error_user)
                } else if (!validEmail) {
                    stringResource(id = R.string.error_email)
                } else if (!validPass) {
                    stringResource(id = R.string.error_password)
                } else {
                    stringResource(id = R.string.error_confirm_password)
                },
                color = Color.Red,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        ButtonPrimary(
            onClicked = {
                isErrorRegister = !(validEmail && validUser && validPass && validRepass)
                isClickedButton = true
                if(!isErrorRegister) loginIntent()
            },
            text = stringResource(R.string.register),
        )
        Spacer(modifier = modifier.height(24.dp))
        ButtonSecondary(
            onClicked = {},
            text = stringResource(R.string.google_login),
        )
    }
}