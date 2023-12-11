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
import com.example.challengeit.ui.dataclass.Challenge
import com.example.challengeit.ui.dataclass.Group
import com.example.challengeit.ui.navigation.Screen
import com.example.challengeit.ui.theme.ChallengeItTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

// Annotation indiquant que l'utilisation de l'API Material3 est expérimentale
@OptIn(ExperimentalMaterial3Api::class)
// Composable principal pour l'écran de groupe
@Composable
fun UserListScreen(navController: NavHostController, group: Group?) {
    // Applique le thème personnalisé ChallengeItTheme
    ChallengeItTheme {
        // Utilise le composant Scaffold pour définir la structure de base de l'écran
        Scaffold(
            bottomBar = { Navigation(navController = navController) }
        ) { innerPadding ->
            // Appelle le composant GroupBody pour définir le contenu principal de l'écran
            if (group != null) {
                UserBody(navController, Modifier.padding(innerPadding), group)
            }
        }
    }
}

// Composable pour le corps principal de l'écran de groupe
@Composable
fun UserBody(navController: NavHostController, modifier: Modifier, group: Group) {

    val userList by remember { mutableStateOf(runBlocking { getUserList(group.id) }) }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Affiche le titre "Classement" avec une taille de police, une couleur et un style spécifiques
        Text(
            text = "Liste des joueurs",
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
                    Text("Nom", fontWeight = FontWeight.Bold)
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



// Composable pour afficher un élément de défi dans la liste
@Composable
fun UserItem(user: User, navController: NavHostController) {
    // Utilise une rangée pour organiser les éléments horizontalement centrés
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = user.displayName)
    }
}

// Fonction suspendue pour récupérer la liste des groupes depuis Firestore

suspend fun getUserList(groupId: String): List<User> {

    Log.d("TEST4",groupId);
    // Obtenir une instance de la base de données Firestore
    val firestore = FirebaseFirestore.getInstance()

    // Effectuer une requête asynchrone pour obtenir un snapshot de la collection "GROUP"
    val snapshot = firestore.collection("group")
        .document(groupId)
        .get()
        .await()

    val group = snapshot.toObject(Group::class.java)
    val users = group?.users


    val resultList = mutableListOf<User>()
    if (users != null) {
        for (user in users) {
            Log.d("TEST2", user.toString());
            val utilisateur = User(id = user, displayName = getDisplayName(user), point = getClassement(user,groupId));
            Log.d("TEST10", utilisateur.displayName);
            Log.d("TEST17", utilisateur.point.toString());
            resultList.add(utilisateur)
        }
    }
    return resultList
}

suspend fun getDisplayName(userId: String): String {
    val firestore = FirebaseFirestore.getInstance()
    val snapshot = firestore.collection("user").document(userId).get().await()

    // Vérifier si le document existe
    if (snapshot.exists()) {
        // Récupérer le displayName du document
        val displayName = snapshot.getString("displayName")
        if (displayName != null) {
            return displayName
        }
    }

    return "Anonymous"
}

suspend fun getClassement(userId: String, groupId: String): Int {
    val firestore = FirebaseFirestore.getInstance()
    val snapshot = firestore.collection("user").document(userId).get().await()

    // Vérifier si le document existe
    if (snapshot.exists()) {
        Log.d("TEST13","bien")
        // Récupérer le map 'classement' du document
        val classementMap = snapshot.get("classement") as? Map<*, *>
        Log.d("TEST15",classementMap.toString())
        Log.d("TEST16",groupId)
        // Vérifier si le map et la clé groupId existent
        if (classementMap != null && classementMap.containsKey(groupId)) {

            Log.d("TEST14","tresbien")
            // Récupérer le classement associé au groupId
            val classement = classementMap[groupId]

            // Log the values for debugging
            println("classementMap: $classementMap")
            println("groupId: $groupId")
            println("classement: $classement")

            // Vérifier si le classement est un nombre
            if (classement is Number) {
                Log.d("TEST11",classement.toString())
                return classement.toInt()
            }
        }
    }
    Log.d("TEST12","nul")
    return 0
}