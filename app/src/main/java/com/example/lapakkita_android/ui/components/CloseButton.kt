package com.example.lapakkita_android.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.lapakkita_android.R

@Composable
fun CloseButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    IconButton(
        onClick = { onClick() },
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(R.string.back)
        )
    }
}