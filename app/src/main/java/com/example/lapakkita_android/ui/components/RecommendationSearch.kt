package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.lapakkita_android.R
import com.example.lapakkita_android.data.local.CategoryList

@Composable
fun RecommendationSearch(
    hideDialog: () -> Unit,
    buttonClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    var selectedCategory by remember { mutableStateOf("") }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .clickable(
                onClick = { },
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            )
        ,
        contentAlignment = Alignment.TopCenter,
    ) {
        IconButton(
            onClick = {
                hideDialog()
                selectedCategory = ""
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.close),
                modifier = Modifier
                    .padding(32.dp)
            )
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 60.dp, horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.select_category),
                fontFamily = FontFamily(Font(R.font.inter_bold)),
                fontSize = 16.sp,
                letterSpacing = 0.em,
                textAlign = TextAlign.Start,
                modifier = modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
            )
            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier
                    .fillMaxHeight()
                    .padding(top = 16.dp)
            ) {
                items(
                    CategoryList.categoryIn,
                    key = { it }
                ) { data ->
                    CategoryCard(
                        item = data,
                        selectedCategory = selectedCategory,
                        itemClicked = {
                            selectedCategory = if (selectedCategory == it) ""
                            else it
                        }
                    )
                }
            }
        }
        ButtonContainer(
            onClicked = {
                buttonClicked(selectedCategory)
            },
            text = stringResource(R.string.search_store),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 28.dp),
        )
    }
}