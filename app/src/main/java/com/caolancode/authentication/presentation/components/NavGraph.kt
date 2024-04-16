package com.caolancode.authentication.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.caolancode.authentication.data.Destination
import com.caolancode.authentication.domain.AuthViewModel
import com.caolancode.authentication.presentation.Home
import com.caolancode.authentication.presentation.SignIn
import com.caolancode.authentication.presentation.SignUp

@Composable
fun SetupNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel
) {
    val isSignedIn by authViewModel.isSignedIn.collectAsState()

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Destination.SignIn.route
    ) {
        composable(Destination.SignIn.route) {
            if (isSignedIn) {
                Home(authViewModel = authViewModel)
            } else {
                SignIn(
                    modifier = Modifier,
                    onNavigateToSignUp = {
                        navHostController.navigate(Destination.SignUp.route)
                    },
                    authViewModel = authViewModel
                )
            }
        }
        composable(Destination.SignUp.route) {
            SignUp(
                modifier = Modifier,
                onNavigateToSignIn = {
                    navHostController.navigate(Destination.SignIn.route)
                },
                authViewModel = authViewModel
            )
        }
        composable(Destination.Home.route) {
            Home(authViewModel = authViewModel)
        }
    }
}