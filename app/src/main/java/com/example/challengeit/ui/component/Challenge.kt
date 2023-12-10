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
import com.example.challengeit.ui.dataclass.Challenge
import com.example.challengeit.ui.dataclass.Group
import com.example.challengeit.ui.theme.ChallengeItTheme
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

// Annotation indiquant que l'utilisation de l'API Material3 est expérimentale
@OptIn(ExperimentalMaterial3Api::class)
// Composable principal pour l'écran de défi
@Composable
fun ChallengeScreen(navController: NavHostController, challenge: Challenge?) {
    // Applique le thème personnalisé ChallengeItTheme
    ChallengeItTheme {
        // Utilise le composant Scaffold pour définir la structure de base de l'écran
        Scaffold(
            bottomBar = { Navigation(navController = navController) }
        ) { innerPadding ->
            // Appelle le composant ChallengeBody pour définir le contenu principal de l'écran
            ChallengeBody(navController, Modifier.padding(innerPadding), challenge)
        }
    }
}

// Composable pour le corps principal de l'écran de défi
@Composable
fun ChallengeBody(navController: NavHostController, modifier: Modifier, challenge: Challenge?) {
    // Utilise une colonne pour organiser les éléments de manière verticale
    Column(modifier = modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Affiche le nom du défi avec une taille de police, une couleur et un style spécifiques
        if (challenge != null) {
            Text(
                text = challenge.name,
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
        // Ajoute un espace vertical
        Spacer(modifier = Modifier.height(16.dp))
        // Affiche la description du défi en utilisant le style de typographie MaterialTheme
        if (challenge != null) {
            Text(
                text = challenge.description,
                style = MaterialTheme.typography.labelLarge
            )
        }
        // Ajoute un autre espace vertical
        Spacer(modifier = Modifier.height(16.dp))
        // Affiche la récompense du défi
        if (challenge != null) {
            Text(
                text = "Récompense : ${challenge.point} pts",
                style = MaterialTheme.typography.labelLarge
            )
        }
        // Utilise une rangée pour organiser les éléments horizontalement avec un espacement à la fin
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            // Ajoute un bouton de retour avec une couleur et une forme spécifiques
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
