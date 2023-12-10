package com.example.challengeit.ui.navigation

sealed class Screen(val route: String){
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Registration : Screen("registration")
    object MainPage : Screen("mainPage")
    object Group : Screen("group/{id}") {
        fun giveId(id: String): String{
            return "group/$id"
        }
    }
    object Challenge : Screen("challenge/{id}") {
        fun giveId(id: String): String{
            return "challenge/$id"
        }
    }
    object JoinGroup : Screen("joinGroup")
    object PrivateGroup : Screen("privateGroup")
    object PublicGroup : Screen("publicGroup")
    object NewGroup : Screen("newGroup")
    object NewChallenge : Screen("newChallenge/{id}") {
        fun giveId(id: String): String{
            return "newChallenge/$id"
        }
    }
    object Leaderboard : Screen("leaderboard")
    object Profile : Screen("profile")
}
