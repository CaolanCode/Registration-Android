package com.caolancode.authentication.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.caolancode.authentication.R
import com.caolancode.authentication.domain.AuthViewModel

@Composable
fun Home(
    authViewModel: AuthViewModel
) {
    val auth by authViewModel.auth.collectAsState()
    val user = auth.currentUser

    Column {
        if (user != null) {
            Text(text = "Welcome ${user.email ?: "User"}")
        }
        Button(
            onClick = {
                authViewModel.signOutOfAccount()
            }) {
            Text(text = stringResource(id = R.string.signout_button))
        }
    }
}