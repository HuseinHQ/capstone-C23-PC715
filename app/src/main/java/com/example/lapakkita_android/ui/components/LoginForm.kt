package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lapakkita_android.R

@Composable
fun LoginForm(
    signIn: (String, String) -> Unit,
    googleSignIn: () -> Unit,
    modifier: Modifier = Modifier,
){
    var isErrorLogin by remember { mutableStateOf(false) }
    var isClickedButton by remember { mutableStateOf(false) }

    var validPass by remember { mutableStateOf(false) }
    var validEmail by remember { mutableStateOf(false) }

    var email by remember{ mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    Column(modifier = modifier
        .fillMaxWidth()) {
        EmailForm(
            emailTextValidate = {
                validEmail = it
                if(isClickedButton) isErrorLogin = !(validEmail  && validPass)
            },
            emailText = {
                email = it
            }
        )
        Spacer(modifier = modifier.height(16.dp))
        PasswordForm(
            passTextValidate = {
                validPass = it
                if(isClickedButton) isErrorLogin = !(validEmail  && validPass)
            },
            passText = {
                pass = it
            },
            rePassTextValidate = {},
            rePassText = "",
        )
        Spacer(modifier = modifier.height(16.dp))
        if(isErrorLogin) {
            Text(
                text =
                if (!validEmail) {
                    stringResource(id = R.string.error_email)
                } else {
                    stringResource(id = R.string.error_password)
                },
                color = Color.Red,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = modifier.height(16.dp))
        ButtonPrimary(
            onClicked = {
                isErrorLogin = !(validEmail  && validPass)
                isClickedButton = true
                if(!isErrorLogin) signIn(email, pass)
            },
            text = stringResource(R.string.login),
        )
        Spacer(modifier = modifier.height(24.dp))
        ButtonSecondary(
            onClicked = {googleSignIn()},
            text = stringResource(R.string.google_login)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}