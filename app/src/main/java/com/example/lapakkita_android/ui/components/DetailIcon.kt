package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.lapakkita_android.R

@Composable
fun DetailIcon(
    editBookmark: (Boolean) -> Unit,
    onClickShare: () -> Unit,
    enableStatus: Boolean,
    modifier: Modifier = Modifier
){
    var isBookmarkClicked by remember{ mutableStateOf(enableStatus) }
    var isShareClicked by remember{ mutableStateOf(false) }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            onClick = {
                isBookmarkClicked = !isBookmarkClicked
                editBookmark(isBookmarkClicked)
            }
        ) {
          Icon(
              painter = if(isBookmarkClicked) painterResource(R.drawable.baseline_bookmark_24)
              else painterResource(R.drawable.baseline_bookmark_border_24),
              contentDescription = stringResource(R.string.add_bookmark))
        }

        IconButton(
            onClick = {
                isShareClicked = true
                onClickShare()
            },
            modifier = Modifier
        ) {
          Icon(
              imageVector = if(isShareClicked) Icons.Filled.Share
              else Icons.Outlined.Share,
              contentDescription = stringResource(R.string.add_bookmark))
        }
    }
}