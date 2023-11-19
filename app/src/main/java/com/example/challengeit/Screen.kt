package com.example.challengeit

sealed class Screen(val route: String){
    object Home : Screen("home")
    object Login : Screen("login")
    object Registration : Screen("registration")
}
