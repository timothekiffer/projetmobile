package com.example.challengeit.ui.theme

sealed class Screen(val route: String){
    object Home : Screen("home")
    object Detail : Screen("detail")
}
