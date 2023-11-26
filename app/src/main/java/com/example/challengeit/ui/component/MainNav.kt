package com.example.challengeit.ui.component

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.ui.dataclass.Challenge
import com.example.challengeit.ui.dataclass.Group
import com.example.challengeit.ui.navigation.Screen

@Composable
fun MainNav(activity: ComponentActivity?) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainPage.route) {
        composable(Screen.MainPage.route) {
            val groups = listOf(
                Group(name = "Groupe UQAC", description = ""),
                Group(name = "Groupe 2", description = ""),
                Group(name = "Groupe 3", description = "")
            )
            MainPageScreen(groups, navController)
        }
        composable(Screen.Group.route) {backStackEntry ->
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            val challenges = listOf(
                Challenge(name = "Faire 300 pas en 1 minute", description = "Pour valider le défi, tu dois faire 1000 pas en 1 minute, cela devra être filmé et uploadé sur l’appli", point = 5),
                Challenge(name = "Prendre un selfie devant la Tour Eiffel", description = "", point = 30),
                Challenge(name = "Prendre un bain de minuit", description = "", point = 10),
                Challenge(name = "Danser la macarena sur une place publique", description = "", point = 20)
            )
            GroupScreen(challenges, navController, id)
        }
        composable(Screen.JoinGroup.route) {
            JoinGroupScreen(navController)
        }
        composable(Screen.Challenge.route) {
            val challenge = Challenge(name = "Faire 300 pas en 1 minute", description = "Pour valider le défi, tu dois faire 1000 pas en 1 minute, cela devra être filmé et uploadé sur l’appli", point = 5)
            ChallengeScreen(challenge, navController)
        }
        composable(Screen.PrivateGroup.route) {
            PrivateGroupScreen(navController)
        }
        composable(Screen.PublicGroup.route) {
            val groups = listOf(
                Group(name = "Groupe France", description = ""),
                Group(name = "Groupe Canada", description = ""),
                Group(name = "Groupe 3", description = "")
            )
            PublicGroupScreen(groups, navController)
        }
        composable(Screen.NewGroup.route) {
            NewGroupScreen(navController)
        }
        composable(Screen.NewChallenge.route) {
            NewChallengeScreen(navController)
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