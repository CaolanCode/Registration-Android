package com.caolancode.authentication.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.caolancode.authentication.R
import com.caolancode.authentication.domain.AuthViewModel

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    authViewModel: AuthViewModel
) {
    val passwordVisibility by authViewModel.passwordVisibility.collectAsState()

    TextField(
        value = value,
        onValueChange = onValueChange,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        label = {
            Text(text = stringResource(id = R.string.password_label))
        },
        trailingIcon = {
            val image = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            IconButton(onClick = {
                authViewModel.setPasswordVisibility()
            }) {
                Icon(
                    imageVector = image,
                    contentDescription = stringResource(id = R.string.password_icon_description)
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        maxLines = 1
    )
}