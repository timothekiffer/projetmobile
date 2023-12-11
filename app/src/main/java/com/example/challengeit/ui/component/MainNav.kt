// Déclaration du package et des importations nécessaires
package com.example.challengeit.ui.component

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

            // Utilise remember pour stocker le résultat de la coroutine
            var group by remember(id) { mutableStateOf<Group?>(null) }

            // Utilise LaunchedEffect pour lancer une coroutine
            LaunchedEffect(id) {
                // Appelle la fonction suspendue getGroupById dans la coroutine
                val result: Group? = getGroupById(id)
                group = result
            }

            // Utilise le groupe dans votre UI
            GroupScreen(navController, group)
        }

        // Écran pour rejoindre un groupe
        composable(Screen.JoinGroup.route) {
            // Appelle le composant représentant l'écran de rejoindre un groupe (JoinGroupScreen)
            JoinGroupScreen(navController)
        }

        // Écran de détails d'un défi
        composable(Screen.Challenge.route) {backStackEntry ->
            // Récupère l'ID du challenge à partir des arguments de la navigation
            val id = backStackEntry.arguments?.getString("id").orEmpty()

            // Utilise remember pour stocker le résultat de la coroutine
            var challenge by remember(id) { mutableStateOf<Challenge?>(null) }

            // Utilise LaunchedEffect pour lancer une coroutine
            LaunchedEffect(id) {
                // Appelle la fonction suspendue getChallengeById dans la coroutine
                val result: Challenge? = getChallengeById(id)
                challenge = result
            }

            // Appelle le composant représentant l'écran de détails du défi (ChallengeScreen)
            ChallengeScreen(navController, challenge)
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
        composable(Screen.NewChallenge.route) {backStackEntry ->
            // Récupère l'ID du groupe à partir des arguments de la navigation
            val id = backStackEntry.arguments?.getString("id").orEmpty()

            // Utilise remember pour stocker le résultat de la coroutine
            var group by remember(id) { mutableStateOf<Group?>(null) }

            // Utilise LaunchedEffect pour lancer une coroutine
            LaunchedEffect(id) {
                // Appelle la fonction suspendue getGroupById dans la coroutine
                val result: Group? = getGroupById(id)
                group = result
            }

            // Appelle le composant représentant l'écran de création d'un nouveau défi (NewChallengeScreen)
            NewChallengeScreen(navController, group)
        }

        // Écran du classement (leaderboard)
        composable(Screen.Leaderboard.route) {
            // Liste factice d'utilisateurs
            val users = listOf(
                User(id = "1", displayName = "Timothé", point = 150),
                User(id = "2", displayName = "Alexandre", point = 89),
                User(id = "3", displayName = "Romain", point = 18)
            )
            // Appelle le composant représentant l'écran du classement (LeaderboardScreen)
            LeaderboardScreen(users, navController)
        }
        
        // Écran pour afficher le profil
        composable(Screen.Profile.route) {
            // Appelle le composant représentant l'écran de profil (ProfileScreen)
            ProfileScreen(navController)

        composable(Screen.UserList.route) {
            backStackEntry ->
            // Récupère l'ID du groupe à partir des arguments de la navigation
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            Log.d("test1",id)

            // Utilise remember pour stocker le résultat de la coroutine
            var group by remember(id) { mutableStateOf<Group?>(null) }

            // Utilise LaunchedEffect pour lancer une coroutine
            LaunchedEffect(id) {
                // Appelle la fonction suspendue getGroupById dans la coroutine
                val result: Group? = getGroupById(id)
                group = result
            }

            // Utilise le groupe dans votre UI
            UserListScreen(navController, group)
        }
        
        composable(Screen.UserListAdmin.route) {
                backStackEntry ->
            // Récupère l'ID du groupe à partir des arguments de la navigation
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            Log.d("test1",id)

            // Utilise remember pour stocker le résultat de la coroutine
            var group by remember(id) { mutableStateOf<Group?>(null) }

            // Utilise LaunchedEffect pour lancer une coroutine
            LaunchedEffect(id) {
                // Appelle la fonction suspendue getGroupById dans la coroutine
                val result: Group? = getGroupById(id)
                group = result
            }

            // Utilise le groupe dans votre UI
            UserListAdminScreen(navController, group)
        }
    }
}
