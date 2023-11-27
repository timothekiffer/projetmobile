// Déclaration du package et des importations nécessaires
package com.example.challengeit.ui.component

import android.text.InputType
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.ui.activity.connexion
import com.example.challengeit.ui.navigation.Screen
import com.example.challengeit.ui.theme.ChallengeItTheme

// Annotation indiquant que l'utilisation de l'API Material3 est expérimentale
@OptIn(ExperimentalMaterial3Api::class)
// Composable représentant l'écran de connexion
@Composable
fun LoginScreen(navController: NavHostController, activity: ComponentActivity) {
    // Déclaration des variables d'état pour l'email, le mot de passe et le résultat de la connexion
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginResult by remember { mutableStateOf(false) }

    // Utilise une colonne pour organiser les éléments de manière verticale avec défilement vertical
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)
        .verticalScroll(rememberScrollState())) {
        // Affiche le titre "Connecte-toi" avec une taille de police, une couleur et un style spécifiques
        Text(
            text = "Connecte-toi",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        // Ajoute un espace vertical
        Spacer(modifier = Modifier.height(16.dp))

        // Affiche le texte "Adresse email" suivi d'un champ de texte pour l'email
        Text(text = "Adresse email")
        TextField(
            value = email,
            onValueChange = { email = it }
        )

        // Ajoute un espace vertical
        Spacer(modifier = Modifier.height(16.dp))

        // Affiche le texte "Mot de Passe" suivi d'un champ de texte pour le mot de passe
        Text(text = "Mot de Passe")
        TextField(
            value = password,
            onValueChange = { password = it }
        )

        // Ajoute un espace vertical
        Spacer(modifier = Modifier.height(16.dp))

        // Ajoute un bouton de connexion avec une action associée
        Button(
            onClick = {
                // Appelle la fonction de connexion avec les informations d'identification fournies
                connexion(email, password, activity)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Connexion")
        }
    }
}

// Fonction de prévisualisation pour l'écran de connexion
@Preview()
@Composable
fun LoginScreenPreview() {
    // Initialise un contrôleur de navigation factice pour la prévisualisation
    val navController = rememberNavController()
    // Initialise une activité factice pour la prévisualisation
    val activity = ComponentActivity()
    // Applique le thème personnalisé ChallengeItTheme et appelle le composant LoginScreen
    ChallengeItTheme {
        LoginScreen(navController, activity)
    }
}
