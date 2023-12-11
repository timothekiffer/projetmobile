// Déclaration du package et des importations nécessaires
package com.example.challengeit.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.challengeit.ui.dataclass.Group
import com.example.challengeit.ui.navigation.Screen
import com.example.challengeit.ui.theme.ChallengeItTheme
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

// Data class représentant un utilisateur avec un identifiant, un nom et un nombre de points
data class User(val id: String, val displayName: String, val point: Int)

// Annotation indiquant que l'utilisation de l'API Material3 est expérimentale
@OptIn(ExperimentalMaterial3Api::class)
// Composable principal pour l'écran du classement
@Composable
fun LeaderboardScreen(group: Group, navController: NavHostController) {
    // Applique le thème personnalisé ChallengeItTheme
    ChallengeItTheme {
        // Utilise le composant Scaffold pour définir la structure de base de l'écran
        Scaffold(
            bottomBar = { Navigation(navController = navController) }
        ) { innerPadding ->
            // Appelle le composant LeaderboardBody pour définir le contenu principal de l'écran
            LeaderboardBody(group, navController, Modifier.padding(innerPadding))
        }
    }
}


// Annotation indiquant que l'utilisation de l'API Material3 est expérimentale
@OptIn(ExperimentalMaterial3Api::class)
// Composable pour le corps principal de l'écran du classement
@Composable
fun LeaderboardBody(group: Group, navController: NavHostController, modifier: Modifier) {
    // Utilise une colonne pour organiser les éléments de manière verticale
    val userList by remember { mutableStateOf(runBlocking { getUserList(group.id) }) }

    Log.w("TEST18","issou");
    Log.d("TEST18", userList[0].point.toString());
    Column(modifier = modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Affiche le titre "Classement" avec une taille de police, une couleur et un style spécifiques
        Text(
            text = "Classement",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        // Ajoute un espace vertical
        Spacer(modifier = Modifier.height(16.dp))

        // Utilise un composant LazyColumn pour afficher la liste des utilisateurs de manière paresseuse
        LazyColumn {
            // Ajoute une ligne d'en-tête avec les noms de colonnes
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Noma", fontWeight = FontWeight.Bold)
                    Text("Points", fontWeight = FontWeight.Bold)
                }
                // Ajoute un espacement après la ligne d'en-tête
                Spacer(modifier = Modifier.height(4.dp))
            }
            // Ajoute des éléments pour chaque utilisateur dans la liste
            items(userList) { user ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.onSurface, shape = MaterialTheme.shapes.small),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Affiche l'ID, le nom et le nombre de points de chaque utilisateur
                    Text(user.displayName)
                    Text(user.point.toString())
                }
            }
        }

        // Ajoute un bouton "Retour aux défis" avec une couleur et une forme spécifiques
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Retour aux défis")
        }
    }
}

