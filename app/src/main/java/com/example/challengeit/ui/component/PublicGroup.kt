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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.ui.dataclass.Group
import com.example.challengeit.ui.navigation.Screen
import com.example.challengeit.ui.theme.ChallengeItTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

// Annotation indiquant que cette fonction utilise des fonctionnalités expérimentales de Compose
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicGroupScreen(navController: NavHostController) {
    // Utilise le thème ChallengeIt
    ChallengeItTheme {
        // Utilise le composant Scaffold pour la mise en page de l'écran
        Scaffold(
            bottomBar = { Navigation(navController = navController) }
        ) { innerPadding ->
            // Utilise le composant PublicGroupBody pour la partie centrale de l'écran
            PublicGroupBody(navController, Modifier.padding(innerPadding))
        }
    }
}

// Composant représentant le corps de l'écran de sélection d'un groupe public
@Composable
fun PublicGroupBody(navController: NavHostController, modifier: Modifier) {
    val groups by remember { mutableStateOf(runBlocking { getPublicGroups() }) }

    // Utilise le composant Column pour organiser les éléments de manière verticale
    Column(modifier = modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Titre de la page
        Text(
            text = "Rejoins un groupe public",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Utilise le composant LazyColumn pour afficher la liste des groupes publics
        LazyColumn (
            modifier = Modifier.fillMaxWidth()
        ){
            items(groups) { group ->
                // Appelle le composant représentant un élément de groupe (GroupItem)
                PublicGroupItem(group, navController)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton de retour
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { navController.popBackStack() },  // Action de retour
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Retour")
            }
        }
    }
}

suspend fun getPublicGroups(): List<Group> {
    val firestore = FirebaseFirestore.getInstance()

    // Effectuer une requête asynchrone pour obtenir un snapshot des groupes publics
    val querySnapshot = firestore.collection("group")
        .whereEqualTo("private", false)  // Filtrer les groupes où "private" est à false
        .get()
        .await()

    val resultList = mutableListOf<Group>()

    for (document in querySnapshot.documents) {
        val groupId = document.id
        val group = document.toObject(Group::class.java)
        if (group != null) {
            group.id = groupId
        };

        group?.let {
            resultList.add(it)
        }
    }

    return resultList
}


// Composant représentant un élément de groupe dans la liste
@Composable
fun PublicGroupItem(group: Group, navController: NavHostController) {
    // Utilise un Row pour organiser les éléments horizontalement
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        // Bouton pour naviguer vers l'écran de détails du groupe avec un ID spécifique
        Button(
            onClick = {
                FirebaseAuth.getInstance().currentUser?.uid?.let { joinPublicGroup(group, it) }
                // Redirige l'utilisateur vers l'écran du groupe fraichement rejoint
                navController.navigate(Screen.MainPage.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = group.name)
        }
    }
    // Espace vertical entre les éléments de groupe
    Spacer(modifier = Modifier.height(16.dp))
}

fun joinPublicGroup(group: Group, userId: String) {
     if (!group.id.isNullOrBlank() && userId.isNotEmpty()) {
        Log.d("FirestoreUpdate", "Group ID: ${group.id}")

         val firestore = FirebaseFirestore.getInstance()

        firestore.collection("group").document(group.id!!)
            .update("users", FieldValue.arrayUnion(userId))
            .addOnSuccessListener {
                Log.d("FirestoreUpdate", "Update successful")
            }
            .addOnFailureListener { e ->
                 Log.e("FirestoreUpdate", "Error updating document", e)
            }
    } else {
        Log.e("FirestoreUpdate", "Invalid group ID or user ID")
    }
}