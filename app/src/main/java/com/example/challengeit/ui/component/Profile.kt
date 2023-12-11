// Déclaration du package et des importations nécessaires
package com.example.challengeit.ui.component

import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.example.challengeit.ui.activity.HomeActivity
import com.example.challengeit.ui.activity.LoginActivity
import com.example.challengeit.ui.activity.MainActivity.Companion.auth
import com.example.challengeit.ui.navigation.Screen
import com.example.challengeit.ui.theme.ChallengeItTheme
import com.google.firebase.auth.FirebaseAuth

// Composable principal pour l'écran de profile
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    // Applique le thème personnalisé ChallengeItTheme
    ChallengeItTheme {
        // Utilise le composant Scaffold pour définir la structure de base de l'écran
        Scaffold(
            bottomBar = { Navigation(navController = navController) }
        ) { innerPadding ->
            // Appelle le composant ChallengeBody pour définir le contenu principal de l'écran
            ProfileBody(navController, Modifier.padding(innerPadding))
        }
    }
}

// Composable pour le corps principal de l'écran de défi
@Composable
fun ProfileBody(navController: NavHostController, modifier: Modifier) {
    val context = LocalContext.current
    val activity = LocalContext.current as? ComponentActivity
    // Utilise une colonne pour organiser les éléments de manière verticale
    Column(modifier = modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Utilise une rangée pour organiser les éléments horizontalement
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            // Ajoute un bouton de déconnexion
            Button(
                onClick = {
                    // Déconnecte l'utilisateur
                    auth.signOut()
                    // affiche un message à l'utilisateur
                    Toast.makeText(context, "Vous avez été déconnecté", Toast.LENGTH_SHORT).show()
                    // Redirige l'utilisateur en démarrant l'activité LoginActivity
                    activity?.startActivity(Intent(activity, LoginActivity::class.java))
                    // Fermeture de l'activité en cours (HomeActivity)
                    activity?.finish()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Déconnexion")
            }
        }
    }
}
