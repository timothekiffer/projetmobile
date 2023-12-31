// Déclaration du package et des importations nécessaires
package com.example.challengeit.ui.component

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.challengeit.R
import com.example.challengeit.ui.dataclass.Group
import com.example.challengeit.ui.navigation.Screen
import com.example.challengeit.ui.theme.ChallengeItTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

// Annotation indiquant que l'utilisation de l'API expérimentale est acceptée
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPageScreen(navController: NavHostController) {
    // Applique le thème ChallengeIt
    ChallengeItTheme {
        // Utilise le composant Scaffold pour définir la structure de la page
        Scaffold(
            // Définit la barre inférieure de navigation avec le composant Navigation
            bottomBar = { Navigation(navController = navController) }
        ) { innerPadding ->
            // Appelle le composant représentant le corps de la page principale (MainPageBody)
            MainPageBody(navController, Modifier.padding(innerPadding))
        }
    }
}

// Composant représentant le corps de la page principale
@Composable
fun MainPageBody(navController: NavHostController, modifier: Modifier) {
    // Déclare la liste des groupes
    val groups by remember { mutableStateOf(runBlocking { getGroupsForCurrentUser(FirebaseAuth.getInstance().currentUser!!.uid) }) }
    // Utilise un Column pour organiser les éléments verticalement
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Titre de la page
        Text(
            text = "Mes groupes de défis",
            style = LocalTextStyle.current.copy(color = colorResource(id = R.color.purple)),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Utilise un LazyColumn pour afficher la liste de groupes de manière efficace
        LazyColumn (
            modifier = Modifier.fillMaxWidth()
        ){
            items(groups) { group ->
                // Appelle le composant représentant un élément de groupe (GroupItem)
                GroupItem(group, navController)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Bouton pour naviguer vers l'écran de rejoindre un groupe
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { navController.navigate(Screen.JoinGroup.route) },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.purple)),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Ajoute un groupe ici")
            }
        }
    }
}

// Composant représentant un élément de groupe dans la liste
@Composable
fun GroupItem(group: Group, navController: NavHostController) {
    // Utilise un Row pour organiser les éléments horizontalement
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        // Bouton pour naviguer vers l'écran de détails du groupe avec un ID spécifique
        Button(
            onClick = { navController.navigate(Screen.Group.giveId(group.id)) },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.purple_200)),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = group.name)
        }
    }
    // Espace vertical entre les éléments de groupe
    Spacer(modifier = Modifier.height(16.dp))
}
suspend fun getGroupsForCurrentUser(userId: String): List<Group> {
    // Obtenir une instance de la base de données Firestore
    val firestore = FirebaseFirestore.getInstance()

    // Effectuer une requête asynchrone pour obtenir un snapshot de la collection "group"
    val snapshot = firestore.collection("group")
        .whereArrayContains("users", userId)
        .get()
        .await()

    // Convertir le snapshot en une liste d'objets de type Group à l'aide de l'extension toObjects
    val resultList = mutableListOf<Group>()
    for (document in snapshot.documents) {
        val group = document.toObject(Group::class.java)
        if (group != null) {
            group.id = document.id
            resultList.add(group)
        }
    }
    return resultList
}

suspend fun getGroupById(id: String): Group? {
    return withContext(Dispatchers.IO) {
        val firestore = FirebaseFirestore.getInstance()
        val snapshot = firestore.collection("group")
            .document(id)
            .get()
            .await()

        if (snapshot.exists()) {
            val group = snapshot.toObject(Group::class.java)
            group?.id = snapshot.id
            group
        } else {
            null
        }
    }
}

