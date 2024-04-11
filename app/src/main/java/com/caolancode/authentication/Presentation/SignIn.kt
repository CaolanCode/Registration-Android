package com.caolancode.authentication.Presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.caolancode.authentication.Presentation.components.AlreadyUser
import com.caolancode.authentication.Presentation.components.AuthButton
import com.caolancode.authentication.Presentation.components.EmailPasswordInput
import com.caolancode.authentication.R

@Composable
fun SignIn(
    modifier: Modifier,
    onNavigateToSignUp: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val spacerHeight = dimensionResource(id = R.dimen.email_password_spacer)

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailPasswordInput(
            email = email,
            onEmailChange = ::onEmailChange,
            password = password,
            onPasswordChange = ::onPasswordChange
        )
        Spacer(modifier = modifier.height(spacerHeight))
        AuthButton(
            text = stringResource(id = R.string.signin_button),
            onClick = {
                // TODO: log in
            }
        )
        Spacer(modifier = modifier.height(spacerHeight))
        AlreadyUser(
            modifier = modifier,
            question = stringResource(id = R.string.not_user_question),
            navigatePage = onNavigateToSignUp,
            linkText = stringResource(id = R.string.not_user_link)
        )
    }
}