package com.caolancode.authentication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.caolancode.authentication.data.Destination
import com.caolancode.authentication.domain.AuthViewModel
import com.caolancode.authentication.presentation.components.SetupNavGraph
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    private lateinit var authViewModel: AuthViewModel
    private lateinit var auth: FirebaseAuth

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = AuthViewModel()
        auth = Firebase.auth
        authViewModel.setAuth(auth)

        setContent {
            navHostController = rememberNavController()
            val errorMessage by authViewModel.errorMessage.collectAsState()
            val isSignedIn by authViewModel.isSignedIn.collectAsState()

            if (errorMessage != null) {
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
                authViewModel.updateErrorMessage(null)
            }

            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Text(text = stringResource(id = R.string.app_name))
                        },
                        actions = {
                            if (isSignedIn) {
                                IconButton(onClick = {
                                    authViewModel.signOutOfAccount()
                                }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Logout,
                                        contentDescription = stringResource(id = R.string.signout_button)
                                    )
                                }
                            }
                        }
                    )
                },
                bottomBar = {
                    BottomAppBar(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.primary,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .clickable {
                                        val urlIntent = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://github.com/CaolanCode")
                                        )
                                        this@MainActivity.startActivity(urlIntent)
                                    },
                                textAlign = TextAlign.Center,
                                text = stringResource(id = R.string.footer)
                            )
                        }
                    }
                }
            ) {paddingValue ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValue),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SetupNavGraph(
                        modifier = Modifier,
                        navHostController = navHostController,
                        authViewModel = authViewModel
                    )
                }
            }
        }
    }
}