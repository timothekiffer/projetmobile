package com.example.challengeit

sealed class Screen(val route: String){
    object Home : Screen("home")
    object Detail : Screen("detail")
    object Connexion : Screen("connexion")
    object Inscription : Screen("inscription")
}
