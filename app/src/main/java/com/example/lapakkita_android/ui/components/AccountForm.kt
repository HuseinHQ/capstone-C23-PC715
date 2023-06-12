package com.example.lapakkita_android.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.example.lapakkita_android.R

@Composable
fun EmailForm(
    emailTextValidate: (Boolean) -> Unit,
    emailText: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    var text by remember { mutableStateOf(TextFieldValue()) }
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = stringResource(R.string.email)
            )
        },
        value = text,
        onValueChange = { newValue ->
            text = newValue
            emailTextValidate(android.util.Patterns.EMAIL_ADDRESS.matcher(newValue.text).matches())
            emailText(newValue.text)
        },
        shape = RoundedCornerShape(32.dp),
        placeholder = { Text(text = stringResource(id = R.string.email)) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFFEFEFEF),
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = colorResource(R.color.primary),
            unfocusedIndicatorColor = Color.Transparent,
            placeholderColor = Color(0xFF707070),
            textColor = Color.Black,
            cursorColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email,
        ),
        keyboardActions = KeyboardActions(onDone = {}),
        trailingIcon = {
            if (text != TextFieldValue("")){
                IconButton(
                    onClick = {
                        text = TextFieldValue("")
                        emailTextValidate(false)
                    },
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.email),
                        tint = Color(0xFF707070)
                    )
                }
            }
        },
    )
}

@Composable
fun PasswordForm(
    passTextValidate: (Boolean) -> Unit,
    passText: (String) -> Unit,
    rePassTextValidate: (Boolean) -> Unit,
    rePassText: String,
    modifier: Modifier = Modifier,
){
    var text by remember { mutableStateOf(TextFieldValue()) }
    var passVisible by remember { mutableStateOf(false) }
    var filledPass by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        leadingIcon = {
            Icon(
                    imageVector = Icons.Default.Lock,
                contentDescription = stringResource(R.string.password)
            )
        },
        value = text,
        onValueChange = { newValue ->
            text = newValue
            filledPass = newValue.toString() != ""
            passTextValidate(newValue.text.length >= 8)
            passText(newValue.text)
            rePassTextValidate(rePassText==newValue.text)
        },
        shape = RoundedCornerShape(32.dp),
        placeholder = { Text(text = stringResource(id = R.string.password)) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFFEFEFEF),
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = colorResource(R.color.primary),
            unfocusedIndicatorColor = Color.Transparent,
            placeholderColor = Color(0xFF707070),
            textColor = Color.Black,
            cursorColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password,
        ),
        trailingIcon = {
            if(filledPass) {
                IconButton(
                    onClick = { passVisible = !passVisible },
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Icon(
                        painter = if (passVisible) painterResource(R.drawable.baseline_visibility_off_24)
                        else painterResource(R.drawable.visibility_24),
                        contentDescription = if (passVisible) stringResource(R.string.pass_visible_off)
                        else stringResource(R.string.pass_visible_on),
                        tint = Color(0xFF707070)
                    )
                }
            }
        },
        visualTransformation = if(passVisible)VisualTransformation.None else PasswordVisualTransformation()
    )
}
@Composable
fun RePasswordForm(
    rePassTextValidate: (Boolean) -> Unit,
    confirmPass: (String) -> Unit,
    passText: String,
    modifier: Modifier = Modifier,
){
    var text by remember { mutableStateOf(TextFieldValue()) }
    var passVisible by remember { mutableStateOf(false) }
    var filledPass by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        leadingIcon = {
            Icon(
                    imageVector = Icons.Default.Lock,
                contentDescription = stringResource(R.string.confirm_password)
            )
        },
        value = text,
        onValueChange = { newValue ->
            text = newValue
            filledPass = newValue.toString() != ""
            rePassTextValidate(passText==newValue.text)
            confirmPass(newValue.text)

        },
        shape = RoundedCornerShape(32.dp),
        placeholder = { Text(text = stringResource(id = R.string.confirm_password)) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFFEFEFEF),
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = colorResource(R.color.primary),
            unfocusedIndicatorColor = Color.Transparent,
            placeholderColor = Color(0xFF707070),
            textColor = Color.Black,
            cursorColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password,
        ),
        trailingIcon = {
            if(filledPass) {
                IconButton(
                    onClick = { passVisible = !passVisible },
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Icon(
                        painter = if (passVisible) painterResource(R.drawable.baseline_visibility_off_24)
                        else painterResource(R.drawable.visibility_24),
                        contentDescription = if (passVisible) stringResource(R.string.pass_visible_off)
                        else stringResource(R.string.pass_visible_on),
                        tint = Color(0xFF707070)
                    )
                }
            }
        },
        visualTransformation = if(passVisible)VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun UsernameForm(
    userTextValidate: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
){
    var text by remember { mutableStateOf(TextFieldValue()) }
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = stringResource(R.string.user)
            )
        },
        value = text,
        onValueChange = { newValue ->
            text = newValue
            userTextValidate(newValue.text.isNotEmpty())
        },
        shape = RoundedCornerShape(32.dp),
        placeholder = { Text(text = stringResource(id = R.string.user)) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFFEFEFEF),
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = colorResource(R.color.primary),
            unfocusedIndicatorColor = Color.Transparent,
            placeholderColor = Color(0xFF707070),
            textColor = Color.Black,
            cursorColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
        ),
        keyboardActions = KeyboardActions(onDone = {}),
        trailingIcon = {
            if (text != TextFieldValue("")){
                IconButton(
                    onClick = {
                        text = TextFieldValue("")
                        userTextValidate(false)
                    },
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.email),
                        tint = Color(0xFF707070)
                    )
                }
            }
        },
    )
}