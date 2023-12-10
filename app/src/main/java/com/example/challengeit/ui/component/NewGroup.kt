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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.ui.dataclass.Group
import com.example.challengeit.ui.navigation.Screen
import com.example.challengeit.ui.theme.ChallengeItTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

// Composant représentant l'écran de création d'un nouveau groupe
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NewGroupScreen(navController: NavHostController) {
    ChallengeItTheme {
        // Utilise le composant Scaffold pour la mise en page de l'écran
        Scaffold(
            bottomBar = { Navigation(navController = navController) }
        ) { innerPadding ->
            // Utilise le composant NewGroupBody pour la partie centrale de l'écran
            NewGroupBody(navController, Modifier.padding(innerPadding), FirebaseAuth.getInstance().currentUser!!.uid)
        }
    }
}

// Composant représentant le corps de l'écran de création d'un nouveau groupe
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NewGroupBody(navController: NavHostController, modifier: Modifier, userId: String) {
    // États pour stocker les valeurs du nom, de la description et de la visibilité du groupe
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isPrivate by remember { mutableStateOf(false) } // Nouvel état pour la visibilité du groupe

    // Obtient le contexte local et le contrôleur de clavier
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // Obtient une instance de FirebaseFirestore pour interagir avec la base de données Firestore
    val firestore = FirebaseFirestore.getInstance()

    // Utilise le composant Column pour organiser les éléments de manière verticale
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Titre de la page
        Text(
            text = "Création du groupe",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Champ de texte pour le nom du groupe
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nom du groupe") },
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
        Spacer(modifier = Modifier.height(16.dp))

        // Champ de texte pour la description du groupe
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description du groupe") },
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
        Spacer(modifier = Modifier.height(16.dp))

        // Case à cocher pour définir si le groupe est privé ou non
        Checkbox(
            checked = isPrivate,
            onCheckedChange = { isPrivate = it },
            modifier = Modifier.padding(8.dp),
        )
        Text("Groupe privé")

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour créer le groupe
        Button(
            onClick = {
                val code: String = generateRandomCode(6)
                // Crée une instance de Group avec les données fournies
                val group = Group(name = name, description = description, code = code, isPrivate = isPrivate, users= listOf(userId))

                // Ajoute le groupe à la collection "group" de Firestore
                firestore.collection("group").add(group)

                // Efface les champs après l'ajout
                name = ""
                description = ""
                isPrivate = false // Réinitialise la visibilité du groupe

                // Redirige l'utilisateur vers l'écran principal après l'ajout du groupe
                navController.navigate(Screen.MainPage.route)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            // Texte du bouton "Créer"
            Text(text = "Créer")
        }
    }
}

fun generateRandomCode(length: Int): String {
    val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    return (1..length)
        .map { charset[Random.nextInt(0, charset.length)] }
        .joinToString("")
}
