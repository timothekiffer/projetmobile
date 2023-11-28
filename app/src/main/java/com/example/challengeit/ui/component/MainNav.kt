// Déclaration du package et des importations nécessaires
package com.example.challengeit.ui.component

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.ui.dataclass.Challenge
import com.example.challengeit.ui.dataclass.Group
import com.example.challengeit.ui.navigation.Screen

// Composable représentant la navigation principale de l'application
@Composable
fun MainNav(activity: ComponentActivity?) {
    // Initialise un contrôleur de navigation Jetpack Compose
    val navController = rememberNavController()

    // Définition des destinations de navigation avec leurs composants associés
    NavHost(navController = navController, startDestination = Screen.MainPage.route) {
        // Écran principal de l'application
        composable(Screen.MainPage.route) {
            // Appelle le composant représentant l'écran principal (MainPageScreen)
            MainPageScreen(navController)
        }

        // Écran de détails d'un groupe
        composable(Screen.Group.route) { backStackEntry ->
            // Récupère l'ID du groupe à partir des arguments de la navigation
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            Log.d("test",backStackEntry.arguments.toString())
            var group = getGroupById(id);
            // Appelle le composant représentant l'écran de détails du groupe (GroupScreen)
            GroupScreen(navController, id)
        }

        // Écran pour rejoindre un groupe
        composable(Screen.JoinGroup.route) {
            // Appelle le composant représentant l'écran de rejoindre un groupe (JoinGroupScreen)
            JoinGroupScreen(navController)
        }

        // Écran de détails d'un défi
        composable(Screen.Challenge.route) {
            // Défi factice pour la prévisualisation
            val challenge = Challenge(name = "Faire 300 pas en 1 minute", description = "Pour valider le défi, tu dois faire 1000 pas en 1 minute, cela devra être filmé et uploadé sur l’appli", point = 5)
            // Appelle le composant représentant l'écran de détails du défi (ChallengeScreen)
            ChallengeScreen(challenge, navController)
        }

        // Écran pour rejoindre un groupe privé
        composable(Screen.PrivateGroup.route) {
            // Appelle le composant représentant l'écran de rejoindre un groupe privé (PrivateGroupScreen)
            PrivateGroupScreen(navController)
        }

        // Écran pour rejoindre un groupe public
        composable(Screen.PublicGroup.route) {
            // Liste factice de groupes publics
            val groups = listOf(
                Group(name = "Groupe France", description = "",isPrivate = false),
                Group(name = "Groupe Canada", description = "",isPrivate = false),
                Group(name = "Groupe 3", description = "",isPrivate = false)
            )
            // Appelle le composant représentant l'écran de rejoindre un groupe public (PublicGroupScreen)
            PublicGroupScreen(groups, navController)
        }

        // Écran pour créer un nouveau groupe
        composable(Screen.NewGroup.route) {
            // Appelle le composant représentant l'écran de création d'un nouveau groupe (NewGroupScreen)
            NewGroupScreen(navController)
        }

        // Écran pour créer un nouveau défi
        composable(Screen.NewChallenge.route) {
            // Appelle le composant représentant l'écran de création d'un nouveau défi (NewChallengeScreen)
            NewChallengeScreen(navController)
        }

        // Écran du classement (leaderboard)
        composable(Screen.Leaderboard.route) {
            // Liste factice d'utilisateurs
            val users = listOf(
                User(id = 1, name = "Timothé", point = 150),
                User(id = 2, name = "Alexandre", point = 89),
                User(id = 3, name = "Romain", point = 18)
            )
            // Appelle le composant représentant l'écran du classement (LeaderboardScreen)
            LeaderboardScreen(users, navController)
        }
    }
}
