package com.caolancode.authentication.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.caolancode.authentication.R

@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = stringResource(id = R.string.email_label))
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        maxLines = 1
    )
}
