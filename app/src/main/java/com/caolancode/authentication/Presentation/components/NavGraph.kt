package com.caolancode.authentication.Presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.caolancode.authentication.Presentation.SignIn
import com.caolancode.authentication.Presentation.SignUp
import com.caolancode.authentication.data.Destination

@Composable
fun SetupNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Destination.SignIn.route
    ) {
        composable(Destination.SignIn.route) {
            SignIn(
                modifier = Modifier,
                onNavigateToSignUp = {
                    navHostController.navigate(Destination.SignUp.route)
                }
            )
        }
        composable(Destination.SignUp.route) {
            SignUp(
                modifier = Modifier,
                onNavigateToSignIn = {
                    navHostController.navigate(Destination.SignIn.route)
                }
            )
        }

    }
}