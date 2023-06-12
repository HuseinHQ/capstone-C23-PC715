package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.lapakkita_android.R

@Composable
fun ImageChooser(
    cameraClick: () -> Unit,
    galleryClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        ButtonSecondary(
            onClicked = { cameraClick() },
            text = stringResource(R.string.camera),
        )
        Spacer(modifier = Modifier.height(16.dp))
        ButtonSecondary(
            onClicked = { galleryClick() },
            text = stringResource(R.string.gallery),
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}