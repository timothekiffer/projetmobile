package com.example.challengeit.ui.component

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.ui.activity.LoginActivity
import com.example.challengeit.ui.navigation.Screen

@Composable
fun MainNav(activity: ComponentActivity?) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainPage.route) {
        composable(Screen.Welcome.route) {
            WelcomeScreen("Challenge It", navController)
        }
        composable(Screen.Login.route){
            if (activity != null) {
                LoginScreen(navController, activity)
            }
        }
        composable(Screen.Registration.route){
            RegistrationScreen(navController)
        }
        composable(Screen.MainPage.route) {
            val groups = listOf(
                Group(name = "Groupe UQAC"),
                Group(name = "Groupe 2"),
                Group(name = "Groupe 3")
            )
            MainPageScreen(groups, navController)
        }
        composable(Screen.Group.route) {backStackEntry ->
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            val challenges = listOf(
                Challenge(id = 1, name = "Faire 300 pas en 1 minute", description = "Pour valider le défi, tu dois faire 1000 pas en 1 minute, cela devra être filmé et uploadé sur l’appli", point = 5),
                Challenge(id = 2, name = "Prendre un selfie devant la Tour Eiffel", description = "", point = 30),
                Challenge(id = 3, name = "Prendre un bain de minuit", description = "", point = 10),
                Challenge(id = 4, name = "Danser la macarena sur une place publique", description = "", point = 20)
            )
            GroupScreen(challenges, navController, id)
        }
        composable(Screen.JoinGroup.route) {
            JoinGroupScreen(navController)
        }
        composable(Screen.Challenge.route) {
            val challenge = Challenge(id = 1, name = "Faire 300 pas en 1 minute", description = "Pour valider le défi, tu dois faire 1000 pas en 1 minute, cela devra être filmé et uploadé sur l’appli", point = 5)
            ChallengeScreen(challenge, navController)
        }
        composable(Screen.PrivateGroup.route) {
            PrivateGroupScreen(navController)
        }
        composable(Screen.PublicGroup.route) {
            val groups = listOf(
                Group(name = "Groupe France"),
                Group(name = "Groupe Canada"),
                Group(name = "Groupe 3")
            )
            PublicGroupScreen(groups, navController)
        }
        composable(Screen.NewGroup.route) {
            NewGroupScreen(navController)
        }
        composable(Screen.Leaderboard.route) {
            val users = listOf(
                User(id = 1, name = "Timothé", point = 150),
                User(id = 2, name = "Alexandre", point = 89),
                User(id = 3, name = "Romain", point = 18)
            )
            LeaderboardScreen(users, navController)
        }
    }
}