package com.example.challengeit.ui.navigation

sealed class Screen(val route: String){
    object Welcome : Screen("welcome")
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
