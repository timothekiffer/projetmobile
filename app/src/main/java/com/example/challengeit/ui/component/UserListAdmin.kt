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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.challengeit.R
import com.example.challengeit.ui.dataclass.Challenge
import com.example.challengeit.ui.dataclass.Group
import com.example.challengeit.ui.navigation.Screen
import com.example.challengeit.ui.theme.ChallengeItTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

// Annotation indiquant que l'utilisation de l'API Material3 est expérimentale
@OptIn(ExperimentalMaterial3Api::class)
// Composable principal pour l'écran de groupe
@Composable
fun UserListAdminScreen(navController: NavHostController, group: Group?) {
    // Applique le thème personnalisé ChallengeItTheme
    ChallengeItTheme {
        // Utilise le composant Scaffold pour définir la structure de base de l'écran
        Scaffold(
            bottomBar = { Navigation(navController = navController) }
        ) { innerPadding ->
            // Appelle le composant GroupBody pour définir le contenu principal de l'écran
            if (group != null) {
                UserAdminBody(navController, Modifier.padding(innerPadding), group)
            }
        }
    }
}

// Composable pour le corps principal de l'écran de groupe
@Composable
fun UserAdminBody(navController: NavHostController, modifier: Modifier, group: Group) {
    var userList by remember { mutableStateOf(runBlocking { getUserList(group.id) }) }

    var refreshList by remember { mutableStateOf(false) }
    LaunchedEffect(refreshList) {
        userList = runBlocking { getUserList(group.id) }
    }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Affiche le titre "Classement" avec une taille de police, une couleur et un style spécifiques
        Text(
            text = "Liste des joueurs",
            style = LocalTextStyle.current.copy(color = colorResource(id = R.color.purple)),
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
                    Text("Nom", style = LocalTextStyle.current.copy(color = colorResource(id = R.color.purple)), fontWeight = FontWeight.Bold)
                    Text("Action", style = LocalTextStyle.current.copy(color = colorResource(id = R.color.purple)), fontWeight = FontWeight.Bold)
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
                        .border(1.dp, colorResource(id = R.color.purple_200), shape = MaterialTheme.shapes.small),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Affiche l'ID, le nom et le nombre de points de chaque utilisateur
                    Text(user.displayName, style = LocalTextStyle.current.copy(color = colorResource(id = R.color.purple)))
                    Button(
                        onClick = {
                            // Appel de la fonction pour supprimer l'utilisateur de la liste
                            deleteUserFromList(user.id, group.id)
                            refreshList = !refreshList
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(text = "Supprimer")
                    }
                }
            }
        }

        // Ajoute un bouton "Retour aux défis" avec une couleur et une forme spécifiques
        Button(
            onClick = {navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.purple_200)),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Retour aux défis")
        }
    }
}

fun deleteUserFromList(userId: String, groupId: String) {
    runBlocking {
        withContext(Dispatchers.IO) {
            try {
                // Obtenir une instance de la base de données Firestore
                val firestore = FirebaseFirestore.getInstance()

                // Supprimer l'utilisateur de la liste dans la collection "group"
                firestore.collection("group").document(groupId)
                    .update("users", FieldValue.arrayRemove(userId))
                    .await()

                Log.d("UserAdminBody", "Utilisateur supprimé avec succès de la liste.")
            } catch (e: Exception) {
                Log.e("UserAdminBody", "Erreur lors de la suppression de l'utilisateur de la liste.", e)
            }
        }
    }
}


// Composable pour afficher un élément de défi dans la liste
@Composable
fun UserAdminItem(user: User, navController: NavHostController) {
    // Utilise une rangée pour organiser les éléments horizontalement centrés
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "${user.displayName}")
    }
}
