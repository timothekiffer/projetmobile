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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.challengeit.ui.dataclass.Challenge
import com.example.challengeit.ui.dataclass.Group
import com.example.challengeit.ui.dataclass.Proof
import com.example.challengeit.ui.navigation.Screen
import com.example.challengeit.ui.theme.ChallengeItTheme
import com.google.android.gms.tasks.Tasks
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
fun ValidationScreen(navController: NavHostController, group: Group?) {
    // Applique le thème personnalisé ChallengeItTheme
    ChallengeItTheme {
        // Utilise le composant Scaffold pour définir la structure de base de l'écran
        Scaffold(
            bottomBar = { Navigation(navController = navController) }
        ) { innerPadding ->
            // Appelle le composant GroupBody pour définir le contenu principal de l'écran
            if (group != null) {
                ValidationBody(navController, Modifier.padding(innerPadding), group)
            }
        }
    }
}

// Composable pour le corps principal de l'écran de groupe
@Composable
fun ValidationBody(navController: NavHostController, modifier: Modifier, group: Group) {
    var validationList by remember { mutableStateOf(runBlocking { getValidationList(group.id) }) }

    var refreshList by remember { mutableStateOf(false) }
    LaunchedEffect(refreshList) {
        validationList = runBlocking { getValidationList(group.id) }
    }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Affiche le titre "Classement" avec une taille de police, une couleur et un style spécifiques
        Text(
            text = "Liste des validations",
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
                    Text("Nom et défi", fontWeight = FontWeight.Bold)
                    Text("Choix", fontWeight = FontWeight.Bold)
                }
                // Ajoute un espacement après la ligne d'en-tête
                Spacer(modifier = Modifier.height(4.dp))
            }
            // Ajoute des éléments pour chaque utilisateur dans la liste
            items(validationList) { proof ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.onSurface, shape = MaterialTheme.shapes.small),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Affiche le nom de l'utilisateur
                    Text(getDisplayNameChallenge( proof.user))
                    // Ajoute un bouton "Valider"
                    Button(
                        onClick = {
                            //addPointToClassement(proof.challenge,proof.user)
                            deleteProofFromList(proof.id)
                            refreshList = !refreshList
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(text = "Valider")
                    }
                    Button(
                        onClick = {
                            // Appel de la fonction pour supprimer l'utilisateur de la liste
                            deleteProofFromList(proof.id)
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
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Retour aux défis")
        }
    }
}
/**
fun addPointToClassement(challengeId: String, userId: String) {
    val firestore = FirebaseFirestore.getInstance()

    // Récupérer la référence du document du challenge
    val challengeReference = firestore.collection("challenge")
        .document(challengeId)

    // Effectuer une requête asynchrone pour obtenir le snapshot du document
    challengeReference.get()
        .addOnSuccessListener { challengeSnapshot ->
            if (challengeSnapshot.exists()) {
                val challenge = challengeSnapshot.toObject(Challenge::class.java)
                if (challenge != null && challenge.point != null) {
                    val challengePoints = challenge.point
                    Log.d("FirestorePoints", "Challenge Points: $challengePoints")
                } else {
                    Log.e("FirestorePoints", "Challenge Points not found")
                }
            } else {
                Log.e("FirestorePoints", "Challenge document does not exist")
            }
        }
        .addOnFailureListener { e ->
            // Gérer les erreurs éventuelles
            Log.e("FirestorePoints", "Error retrieving challenge points", e)
        }
}
*/
fun deleteProofFromList(proofId: String) {
    // Assurez-vous que proofId n'est pas nul ou vide
    if (proofId.isNullOrEmpty()) {
        Log.e("FirestoreDelete", "Invalid proof ID")
        return
    }

    val firestore = FirebaseFirestore.getInstance()
Log.d("TEST333",proofId);
    // Construction de la référence du document
    val documentReference = firestore.collection("proof").document(proofId)

    // Suppression du document
    documentReference.delete()
        .addOnSuccessListener {
            Log.d("FirestoreDelete", "Document successfully deleted")
        }
        .addOnFailureListener { e ->
            Log.e("FirestoreDelete", "Error deleting document", e)
        }
}

suspend fun getValidationList(groupId: String): List<Proof> {
    val firestore = FirebaseFirestore.getInstance()

    // Effectuer une requête asynchrone pour obtenir les défis en attente de validation
    val querySnapshot = firestore.collection("proof")
        .get()
        .await()
    Log.d("TEST111",querySnapshot.toString());
    val validationList = mutableListOf<Proof>()

    for (document in querySnapshot.documents) {
        val challenge = document.toObject(Proof::class.java)
        challenge?.let {
            validationList.add(it)
        }
    }
    Log.d("TEST111",validationList.toString());
    return validationList
}

fun getDisplayNameChallenge(userId: String): String {
    return try {
        val firestore = FirebaseFirestore.getInstance()
        val snapshot = Tasks.await(firestore.collection("user").document(userId).get())

        // Vérifier si le document existe
        if (snapshot.exists()) {
            // Récupérer le displayName du document
            val displayName = snapshot.getString("displayName")
            displayName ?: "Anonymous"
        } else {
            "Anonymous"
        }
    } catch (e: Exception) {
        // Gérer les erreurs éventuelles ici
        "Anonymous"
    }
}