package com.caolancode.authentication.presentation

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
import com.caolancode.authentication.R
import com.caolancode.authentication.domain.AuthViewModel
import com.caolancode.authentication.presentation.components.AlreadyUser
import com.caolancode.authentication.presentation.components.AuthButton
import com.caolancode.authentication.presentation.components.EmailTextField
import com.caolancode.authentication.presentation.components.PasswordTextField

@Composable
fun SignIn(
    modifier: Modifier,
    onNavigateToSignUp: () -> Unit,
    authViewModel: AuthViewModel
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
        EmailTextField(
            value = email,
            onValueChange = ::onEmailChange
        )
        Spacer(modifier = Modifier.height(spacerHeight))
        PasswordTextField(
            value = password,
            onValueChange = ::onPasswordChange,
            authViewModel = authViewModel
        )
        AuthButton(
            text = stringResource(id = R.string.signin_button),
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    authViewModel.signInToAccount(email, password)
                } else {
                    authViewModel.updateErrorMessage("Must enter email and password")
                }
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