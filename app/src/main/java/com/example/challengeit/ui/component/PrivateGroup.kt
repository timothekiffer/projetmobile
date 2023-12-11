// Déclaration du package et des importations nécessaires
package com.example.challengeit.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.challengeit.ui.dataclass.Group
import com.example.challengeit.ui.navigation.Screen
import com.example.challengeit.ui.theme.ChallengeItTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

// Composant représentant l'écran de saisie du code pour rejoindre un groupe privé
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivateGroupScreen(navController: NavHostController) {
    ChallengeItTheme {
        // Utilise le composant Scaffold pour la mise en page de l'écran
        Scaffold(
            bottomBar = { Navigation(navController = navController) }
        ) { innerPadding ->
            // Utilise le composant PrivateGroupBody pour la partie centrale de l'écran
            PrivateGroupBody(navController, Modifier.padding(innerPadding), FirebaseAuth.getInstance().currentUser!!.uid)
        }
    }
}

// Composant représentant le corps de l'écran de saisie du code pour rejoindre un groupe privé
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun PrivateGroupBody(navController: NavHostController, modifier: Modifier, userId: String) {
    var code by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Utilise remember pour stocker le résultat de la coroutine
    var group by remember(code) { mutableStateOf<Group?>(null) }

    // Utilise LaunchedEffect pour lancer une coroutine
    LaunchedEffect(code) {
        // Appelle la fonction suspendue getGroupById dans la coroutine
        val result: Group? = getGroupByCode(code)
        group = result
    }

    // Utilise le composant Column pour organiser les éléments de manière verticale
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Titre de la page
        Text(
            text = "Saisie le code du groupe",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Champ de texte pour la saisie du code du groupe
        TextField(
            value = code,
            onValueChange = { code = it.uppercase() },
            label = { Text("Code du groupe") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Cache le clavier virtuel lorsque l'utilisateur appuie sur "Done"
                    keyboardController?.hide()
                }
            ),
        )

        // Texte d'information sur le format du code du groupe
        Text(
            text = "Le code d’un groupe contient 6 caractères alphanumériques (des chiffres et des lettres)",
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour rejoindre le groupe
        Button(
            onClick = {
                if (code == group?.code){
                    joinGroupForCurrentUser(userId, group!!)
                    // Redirige l'utilisateur vers l'écran du groupe fraichement rejoint
                    navController.navigate(Screen.Group.route)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            // Texte du bouton "Rejoindre"
            Text(text = "Rejoindre")
        }
    }
}

fun joinGroupForCurrentUser(userId: String, group: Group) {
    // Obtenir une instance de la base de données Firestore
    val firestore = FirebaseFirestore.getInstance()
    // Ajoute la nouvelle valeur à la liste users du document créé
    group.id.let {
        firestore.collection("group").document(it)
            .update("users", FieldValue.arrayUnion(userId))
    }
}

suspend fun getGroupByCode(code: String): Group? {
    return withContext(Dispatchers.IO) {
        val snapshot = FirebaseFirestore.getInstance()
            .collection("group")
            .whereEqualTo("code", code)
            .get()
            .await()

        if (!snapshot.isEmpty) {
            val group = snapshot.documents[0].toObject(Group::class.java)
            group?.id = snapshot.documents[0].id
            group
        } else {
            null
        }
    }
}
