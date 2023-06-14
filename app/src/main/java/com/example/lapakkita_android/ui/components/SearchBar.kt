package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.lapakkita_android.R
import com.example.lapakkita_android.data.local.Result
import com.example.lapakkita_android.ui.viewmodel.ExploreViewModel

@Composable
fun SearchBar(
    isOpened: (Boolean) -> Unit,
    showDialog: () -> Unit,
    onSearch: (String) -> Unit,
    viewModel: ExploreViewModel,
    modifier: Modifier = Modifier,
){
    var text by remember { mutableStateOf(TextFieldValue()) }
    var opened by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(opened){
        if(opened){
            focusRequester.requestFocus()
        }
    }

    Column(
        modifier = modifier
    ) {
        TextField(
            leadingIcon = {
                if(!opened) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = colorResource(R.color.primary),
                        contentDescription = stringResource(R.string.search_bar)
                    )
                }
                else{
                    IconButton(onClick = {
                        opened = false
                        isOpened(false)
                        if(text.text == "") {
                            onSearch("")
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            tint = colorResource(R.color.primary),
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            },
            value = text,
            onValueChange = { newValue ->
                text = newValue
            },
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text(
                text = stringResource(id = R.string.search_store),
                fontFamily = FontFamily(Font(R.font.inter_medium)),
            ) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                placeholderColor = Color(0xFF707070),
                disabledPlaceholderColor = Color(0xFF707070),
                textColor = Color.Black,
                disabledTextColor = Color.Black,
                cursorColor = colorResource(R.color.primary)
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text,
            ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        opened = false
                        showDialog()
                    },
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_tune_24),
                        contentDescription = stringResource(R.string.custom_search),
                        tint = colorResource(R.color.primary)
                    )
                }
            },
            enabled = opened,
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(10.dp)
                )
                .focusRequester(focusRequester)
                .clickable(
                    onClick = {
                        opened = true
                        isOpened(true)
                    },
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }
                ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    opened = false
                    isOpened(false)
                    onSearch(text.text)
                    viewModel.addHistory(text.text)
                }
            ),
            singleLine = true,
        )
        if(opened) {
            viewModel.getHistoryList()
            viewModel.uiStateHistory.collectAsState(initial = Result.Loading).value.let { state ->
                if (state is Result.Success) {
                    HistoryList(
                        listItem = state.data,
                        onItemClick = {
                            text = TextFieldValue(
                                text = it,
                                selection = TextRange(it.length)
                            )
                            opened = false
                            onSearch(it)
                            isOpened(false)
                            viewModel.addHistory(it)
                        },
                        onDeleteClick = {
                            viewModel.deleteHistory(it)
                            opened = true
                            viewModel.getHistoryList()
                        }
                    )
                }
            }
        }
    }
}