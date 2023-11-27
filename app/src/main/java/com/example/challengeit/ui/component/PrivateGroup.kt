// Déclaration du package et des importations nécessaires
package com.example.challengeit.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.ui.navigation.Screen
import com.example.challengeit.ui.theme.ChallengeItTheme

// Composant représentant l'écran de saisie du code pour rejoindre un groupe privé
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivateGroupScreen(navController: NavHostController) {
    ChallengeItTheme {
        // Utilise le composant Scaffold pour la mise en page de l'écran
        Scaffold(
            bottomBar = { Navigation(navController = navController) }
        ) { innerPadding ->
            // Utilise le composant PrivateGroupBody pour la partie centrale de l'écran
            PrivateGroupBody(navController, Modifier.padding(innerPadding))
        }
    }
}

// Composant représentant le corps de l'écran de saisie du code pour rejoindre un groupe privé
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivateGroupBody(navController: NavHostController, modifier: Modifier) {
    // Utilise le composant Column pour organiser les éléments de manière verticale
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Titre de la page
        Text(
            text = "Saisie le code du groupe",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Champ de texte pour la saisie du code du groupe
        TextField(
            value = "ABC123",  // Valeur par défaut du code (à remplacer par la logique réelle)
            onValueChange = {}
        )

        // Texte d'information sur le format du code du groupe
        Text(
            text = "Le code d’un groupe contient 6 caractères alphanumériques (des chiffres et des lettres)",
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour rejoindre le groupe
        Button(
            onClick = { navController.navigate(Screen.Group.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            // Texte du bouton "Rejoindre"
            Text(text = "Rejoindre")
        }
    }
}

// Prévisualisation de l'écran de saisie du code pour rejoindre un groupe privé
@Preview()
@Composable
fun PrivateGroupScreenPreview() {
    // Initialise le contrôleur de navigation
    val navController = rememberNavController()

    // Applique le thème ChallengeIt
    ChallengeItTheme {
        // Affiche l'écran de saisie du code pour rejoindre un groupe privé dans la prévisualisation
        PrivateGroupScreen(navController)
    }
}
