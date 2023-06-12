package com.example.lapakkita_android.ui.components

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.lapakkita_android.R
import com.example.lapakkita_android.ui.activity.LoginActivity

@Composable
fun OnboardButton(
    loginClick: () -> Unit,
    registerClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        ButtonPrimary(
            onClicked = {loginClick()},
            text = stringResource(R.string.login)
        )
        Spacer(modifier = modifier.width(12.dp))
        ButtonSecondary(
            onClicked = { registerClick() },
            text = stringResource(R.string.register),
            modifier = Modifier
                .padding(top = 12.dp)
        )
        Spacer(modifier = modifier.width(12.dp))
    }
}