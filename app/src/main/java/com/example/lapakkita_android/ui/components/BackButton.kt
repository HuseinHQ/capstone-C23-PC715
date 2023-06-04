package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.lapakkita_android.R

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    IconButton(
        onClick = {onClick()},
        modifier = modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(R.string.back)
        )
    }
}