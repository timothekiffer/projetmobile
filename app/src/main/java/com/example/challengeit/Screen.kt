package com.example.challengeit

sealed class Screen(val route: String){
    object Home : Screen("home")
    object Login : Screen("login")
    object Registration : Screen("registration")
    object MainPage : Screen("mainPage")
    object Group : Screen("group")
    object Challenge : Screen("challenge")
    object JoinGroup : Screen("joinGroup")
    object PrivateGroup : Screen("privateGroup")
    object PublicGroup : Screen("publicGroup")
    object NewGroup : Screen("newGroup")
    object Leaderboard : Screen("leaderboard")
}
