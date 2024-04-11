package com.caolancode.authentication.data

sealed class Destination(val route: String) {
    object SignIn: Destination("signin")
    object SignUp: Destination("signup")
    object Home: Destination("home")
}
