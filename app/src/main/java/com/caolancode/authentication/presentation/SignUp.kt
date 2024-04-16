package com.caolancode.authentication.presentation

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.caolancode.authentication.presentation.components.AlreadyUser
import com.caolancode.authentication.presentation.components.AuthButton
import com.caolancode.authentication.presentation.components.EmailTextField
import com.caolancode.authentication.presentation.components.PasswordTextField
import com.caolancode.authentication.R
import com.caolancode.authentication.domain.AuthViewModel

@Composable
fun SignUp(
    modifier: Modifier,
    onNavigateToSignIn: () -> Unit,
    authViewModel: AuthViewModel
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    val spacerHeight = dimensionResource(id = R.dimen.email_password_spacer)
    val context = LocalContext.current

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onConfirmPasswordChange(newPassword: String) {
        confirmPassword = newPassword
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
        Spacer(modifier = Modifier.height(spacerHeight))
        PasswordTextField(
            value = confirmPassword,
            onValueChange = ::onConfirmPasswordChange,
            authViewModel = authViewModel
        )
        Spacer(modifier = modifier.height(spacerHeight))
        AuthButton(
            text = stringResource(id = R.string.signup_button),
            onClick = {
                val checkCSValidation = clientSideValidation(
                                        context,
                                        email,
                                        password,
                                        confirmPassword
                )
                if (checkCSValidation) {
                    authViewModel.createAccount(email, password)
                }
            }
        )
        Spacer(modifier = modifier.height(spacerHeight))
        AlreadyUser(
            modifier = modifier,
            question = stringResource(id = R.string.already_user_question),
            navigatePage = onNavigateToSignIn,
            linkText = stringResource(id = R.string.already_user_link)
        )

    }
}

fun clientSideValidation(
    context: Context,
    email: String,
    password: String,
    confirmPassword: String
): Boolean {
    if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
        Toast.makeText(context, "Information is missing.", Toast.LENGTH_SHORT).show()
        return false
    }
    if (password != confirmPassword) {
        Toast.makeText(context, "The passwords don't match.", Toast.LENGTH_SHORT).show()
        return false
    }
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        Toast.makeText(context, "The email is not valid.", Toast.LENGTH_SHORT).show()
        return false
    }
    if (!isValidPassword(password)) {
        Toast.makeText(context, "The password must be of length 8 and contain a digit, lowercase, uppercase and special character.", Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}

fun isValidPassword(password: String): Boolean {
    var hasDigit = false
    var hasLowercase = false
    var hasUppercase = false
    var hasSpecial = false

    if (password.length < 8) return false

    for (char in password) {
        if (char.isDigit()) {
            hasDigit = true
        } else if (char.isLowerCase()) {
            hasLowercase = true
        } else if (char.isUpperCase()) {
            hasUppercase = true
        } else if (isSpecialChar(char)) {
            hasSpecial = true
        }
    }
    return hasDigit && hasLowercase && hasUppercase && hasSpecial
}

fun isSpecialChar(char: Char): Boolean {
    val specialChars = listOf('@', '#', '$', '%', '^', '&', '+', '=')
    return specialChars.contains(char)
}