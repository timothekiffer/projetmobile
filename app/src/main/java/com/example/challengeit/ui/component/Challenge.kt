// Déclaration du package et des importations nécessaires
package com.example.challengeit.ui.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.R
import com.example.challengeit.ui.dataclass.Challenge
import com.example.challengeit.ui.dataclass.Group
import com.example.challengeit.ui.dataclass.Proof
import com.example.challengeit.ui.theme.ChallengeItTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
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
            if (challenge != null) {
                ChallengeBody(navController, Modifier.padding(innerPadding), challenge, FirebaseAuth.getInstance().currentUser!!.uid)
            }
        }
    }
}

// Composable pour le corps principal de l'écran de défi
@Composable
fun ChallengeBody(navController: NavHostController, modifier: Modifier, challenge: Challenge, userId: String) {
    // Obtient une instance de FirebaseFirestore pour interagir avec la base de données Firestore
    val firestore = FirebaseFirestore.getInstance()

    val isValid by remember { mutableStateOf(runBlocking { checkProofByChallengeAndCurrentUser(challenge?.id ?: "", userId) }) }

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
                style = LocalTextStyle.current.copy(color = colorResource(id = R.color.purple)),
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
                style = LocalTextStyle.current.copy(color = colorResource(id = R.color.purple)),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Ajoute un autre espace vertical

        Spacer(modifier = Modifier.height(16.dp))
        // Affiche la récompense du défi
        if (challenge != null) {
            Text(
                text = "Récompense : ${challenge.point} pts",
                style = LocalTextStyle.current.copy(color = colorResource(id = R.color.purple)),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Ajoute un espace vertical
        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour concourir
        if (!isValid) {
            Button(
                onClick = {
                    // Crée une instance de Proof avec les données fournies
                    val proof = Proof(challenge = challenge?.id ?: "", user = userId, valid = true)

                    // Ajoute la preuve à la collection "proof" de Firestore
                    firestore.collection("proof").add(proof)

                    // Retourne à la page d'avant
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.purple)),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Concourir")
            }
        } else {
            Text(
                text = "Vous avez déjà concouru",
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
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.purple_200)),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
                Text(
                    text = "Retour",
                    color = Color.White
                )
            }
        }
    }
}

suspend fun checkProofByChallengeAndCurrentUser(challengeId: String, userId: String): Boolean {
    // Obtenir une instance de la base de données Firestore
    val firestore = FirebaseFirestore.getInstance()

    val snapshot = firestore.collection("proof")
        .whereEqualTo("user", userId)
        .whereEqualTo("challenge", challengeId)
        .get()
        .await()

    if (!snapshot.isEmpty) {
        val document = snapshot.documents[0]
        val proof = document.toObject(Proof::class.java)
        if(proof?.valid == true){
            return true
        }
    }
    return false
}
