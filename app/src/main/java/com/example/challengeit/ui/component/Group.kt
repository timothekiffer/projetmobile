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
import com.example.challengeit.ui.dataclass.Challenge
import com.example.challengeit.ui.dataclass.Group
import com.example.challengeit.ui.navigation.Screen
import com.example.challengeit.ui.theme.ChallengeItTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

// Annotation indiquant que l'utilisation de l'API Material3 est expérimentale
@OptIn(ExperimentalMaterial3Api::class)
// Composable principal pour l'écran de groupe
@Composable
fun GroupScreen(navController: NavHostController, group: Group?) {
    // Applique le thème personnalisé ChallengeItTheme
    ChallengeItTheme {
        // Utilise le composant Scaffold pour définir la structure de base de l'écran
        Scaffold(
            bottomBar = { Navigation(navController = navController) }
        ) { innerPadding ->
            // Appelle le composant GroupBody pour définir le contenu principal de l'écran
            if (group != null) {
                GroupBody(navController, Modifier.padding(innerPadding), group)
            }
        }
    }
}

// Composable pour le corps principal de l'écran de groupe
@Composable
fun GroupBody(navController: NavHostController, modifier: Modifier, group: Group) {
    // Déclare la liste des défis
    val challenges by remember { mutableStateOf(runBlocking { getChallenges(group.id) }) }
    val isAdmin by remember  { mutableStateOf(runBlocking { checkIfUserIsAdmin(group.id) }) }
    // Utilise une colonne pour organiser les éléments de manière verticale
    Column(modifier = modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Affiche le nom du groupe avec une taille de police, une couleur et un style spécifiques
        Text(
            text = "Nom du groupe : " + group.name,
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        // Ajoute un espace vertical
        Spacer(modifier = Modifier.height(16.dp))

        // Affiche le code du groupe
        Text(
            text = "Partage ce code avec tes amis : " + group.code,
            color = Color.Black,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )

        // Utilise une rangée pour organiser les éléments horizontalement avec un espacement au début
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            // Affiche le titre "Défis en cours" avec une taille de police, une couleur et un style spécifiques
            Text(
                text = "Défis en cours :",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        // Ajoute un autre espace vertical
        Spacer(modifier = Modifier.height(16.dp))

        // Utilise une colonne pour organiser les éléments de manière verticale avec un espacement à la fin
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            // Utilise le composant ChallengeItem pour chaque élément de la liste de défis
            items(challenges) { challenge ->
                ChallengeItem(challenge, navController)
            }
        }
        // Ajoute un bouton "Nouveau défi" avec une couleur et une forme spécifiques
        Button(
            onClick = { navController.navigate(Screen.NewChallenge.giveId(group.id)) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Nouveau défi")
        }
        // Ajoute un autre espace vertical
        Spacer(modifier = Modifier.height(16.dp))
        // Ajoute un bouton "Classement" avec une couleur et une forme spécifiques
        Button(
            onClick = { navController.navigate(Screen.Leaderboard.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Classement")
        }
        // Ajoute un autre espace vertical
        Spacer(modifier = Modifier.height(16.dp))

        if (isAdmin) {
            Button(
                onClick = { navController.navigate(Screen.UserListAdmin.giveId(group.id)) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Gérer les utilisateurs")
            }
        }
        Button(
            onClick = { navController.navigate(Screen.UserList.giveId(group.id)) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Liste des utilisateurs")
        }
        
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


suspend fun checkIfUserIsAdmin(groupId: String): Boolean {
    // Obtiens l'ID de l'utilisateur courant
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    // Vérifie si l'utilisateur courant est admin du groupe
    if (currentUserId != null) {

        val groupSnapshot = FirebaseFirestore.getInstance().collection("group")
            .document(groupId)
            .get()
            .await()

        // Vérifie si l'ID de l'utilisateur courant est présent dans la liste des administrateurs du groupe
        val adminsList = groupSnapshot.toObject(Group::class.java)?.admins
        if (adminsList != null) {
            return adminsList.contains(currentUserId)
        }
    }

    return false
}

// Composable pour afficher un élément de défi dans la liste
@Composable
fun ChallengeItem(challenge: Challenge, navController: NavHostController) {
    // Utilise une rangée pour organiser les éléments horizontalement centrés
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        // Utilise un bouton pour représenter l'élément de défi avec une couleur et une forme spécifiques
        Button(
            onClick = { navController.navigate(Screen.Challenge.giveId(challenge.id)) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            // Affiche le nom du défi et sa récompense
            Text(text = "${challenge.name} - ${challenge.point} pts")
        }
    }
}

// Fonction suspendue pour récupérer la liste des groupes depuis Firestore
suspend fun getChallenges(id: String): List<Challenge> {
    // Obtenir une instance de la base de données Firestore
    val firestore = FirebaseFirestore.getInstance()

    // Effectuer une requête asynchrone pour obtenir un snapshot de la collection "challenge"
    val snapshot = firestore.collection("challenge")
        .whereEqualTo("group", id)
        .get()
        .await()

    // Convertir le snapshot en une liste d'objets de type Challenge à l'aide de l'extension toObjects
    val resultList = mutableListOf<Challenge>()
    for (document in snapshot.documents) {
        val challenge = document.toObject(Challenge::class.java)
        if (challenge != null){
            challenge.id = document.id
            resultList.add(challenge)
        }
    }
    return resultList
}

suspend fun getChallengeById(id: String): Challenge? {
    return withContext(Dispatchers.IO) {
        val firestore = FirebaseFirestore.getInstance()
        val snapshot = firestore.collection("challenge")
            .document(id)
            .get()
            .await()

        if (snapshot.exists()) {
            val challenge = snapshot.toObject(Challenge::class.java)
            if (challenge != null) {
                challenge.id = snapshot.id
            }
            challenge
        } else {
            null
        }
    }
}