// Déclaration du package et des importations nécessaires
package com.example.challengeit.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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

// Annotation indiquant que l'utilisation de l'API Material3 est expérimentale
@OptIn(ExperimentalMaterial3Api::class)
// Composable principal pour l'écran de rejoindre un groupe
@Composable
fun JoinGroupScreen(navController: NavHostController) {
    // Applique le thème personnalisé ChallengeItTheme
    ChallengeItTheme {
        // Utilise le composant Scaffold pour définir la structure de base de l'écran
        Scaffold(
            bottomBar = { Navigation(navController = navController) }
        ) { innerPadding ->
            // Appelle le composant JoinGroupBody pour définir le contenu principal de l'écran
            JoinGroupBody(navController, Modifier.padding(innerPadding))
        }
    }
}

// Composable pour le corps principal de l'écran de rejoindre un groupe
@Composable
fun JoinGroupBody(navController: NavHostController, modifier: Modifier) {
    // Utilise une colonne pour organiser les éléments de manière verticale
    Column(modifier = modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Utilise une rangée pour organiser les éléments horizontalement avec un espacement au début
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            // Affiche le texte "Tu veux.." avec une taille de police, une couleur et un style spécifiques
            Text(
                text = "Tu veux..",
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
        // Ajoute un espace vertical
        Spacer(modifier = Modifier.height(16.dp))
        // Ajoute un bouton "Rejoindre un groupe privé" avec une couleur et une forme spécifiques
        Button(
            onClick = { navController.navigate(Screen.PrivateGroup.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Rejoindre un groupe privé")
        }
        // Ajoute un autre espace vertical
        Spacer(modifier = Modifier.height(16.dp))
        // Ajoute un bouton "Rejoindre un groupe public" avec une couleur et une forme spécifiques
        Button(
            onClick = { navController.navigate(Screen.PublicGroup.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Rejoindre un groupe public")
        }
        // Ajoute un autre espace vertical
        Spacer(modifier = Modifier.height(16.dp))
        // Ajoute un bouton "Créer un groupe" avec une couleur et une forme spécifiques
        Button(
            onClick = { navController.navigate(Screen.NewGroup.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Créer un groupe")
        }
        // Ajoute un autre espace vertical
        Spacer(modifier = Modifier.height(16.dp))
        // Utilise une rangée pour organiser les éléments horizontalement avec un espacement à la fin
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            // Ajoute un bouton "Retour" avec une couleur et une forme spécifiques
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Retour")
            }
        }
    }
}

// Fonction de prévisualisation pour l'écran de rejoindre un groupe
@Preview()
@Composable
fun JoinGroupScreenPreview() {
    // Initialise un contrôleur de navigation factice pour la prévisualisation
    val navController = rememberNavController()
    // Applique le thème personnalisé ChallengeItTheme et appelle le composant JoinGroupScreen
    ChallengeItTheme {
        JoinGroupScreen(navController)
    }
}
