// Déclaration du package et des importations nécessaires
package com.example.challengeit.ui.component

import android.text.InputType
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.R
import com.example.challengeit.ui.activity.connexion
import com.example.challengeit.ui.navigation.Screen
import com.example.challengeit.ui.theme.ChallengeItTheme

// Annotation indiquant que l'utilisation de l'API Material3 est expérimentale
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
// Composable représentant l'écran de connexion
@Composable
fun LoginScreen(navController: NavHostController, activity: ComponentActivity) {
    // Déclaration des variables d'état pour l'email, le mot de passe et le résultat de la connexion
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginResult by remember { mutableStateOf(false) }

    // Obtient le contexte local et le contrôleur de clavier
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        // Utilisation de la couleur d'arrière-plan définie dans Color
        color = colorResource(id = R.color.purple) // Utilisez l'ID de la couleur
    ) {
        // Affiche le titre avec une taille de police, une couleur et un style spécifiques
        Text(
            text = "Connecte-toi",
            color = Color.White,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 100.dp)
        )

        // Ajoute un espace vertical
        Spacer(modifier = Modifier.height(20.dp))

        // Utilise une colonne pour organiser les éléments de manière verticale avec défilement vertical
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Affiche le texte "Identifiant" suivi d'un champ de texte pour l'email
            Text(
                text = "Identifiant",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = {
                    Text(
                        text = "Email",
                        style = LocalTextStyle.current.copy(color = colorResource(id = R.color.purple_200))
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(id = R.color.purple), // Couleur du texte
                    containerColor = Color.White, // Couleur de fond
                    cursorColor = colorResource(id = R.color.purple), // Couleur du curseur
                    focusedIndicatorColor = colorResource(id = R.color.purple_200), // Couleur de l'indicateur lorsqu'il est activé
                ),
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

            // Ajoute un espace vertical
            Spacer(modifier = Modifier.height(20.dp))

            // Affiche le texte "Mot de passe sécurisé" suivi d'un champ de texte pour le mot de passe
            Text(
                text = "Mot de passe sécurisé",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Text(
                        text = "Mot de passe",
                        style = LocalTextStyle.current.copy(color = colorResource(id = R.color.purple_200))
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(id = R.color.purple), // Couleur du texte
                    containerColor = Color.White, // Couleur de fond
                    cursorColor = colorResource(id = R.color.purple), // Couleur du curseur
                    focusedIndicatorColor = colorResource(id = R.color.purple_200), // Couleur de l'indicateur lorsqu'il est activé
                ),
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

            // Ajoute un espace vertical
            Spacer(modifier = Modifier.height(20.dp))

            // Ajoute un bouton de connexion avec une action associée
            Button(
                onClick = {
                    // Appelle la fonction de connexion avec les informations d'identification fournies
                    connexion(email, password, activity)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "Connexion",
                    color = colorResource(id = R.color.purple)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            // Ajouter un bouton "Retour" avec une icône de flèche vers la gauche
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
                Text(text = "Retour")
            }
        }

    }
}

// Fonction de prévisualisation pour l'écran de connexion
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    // Initialise un contrôleur de navigation factice pour la prévisualisation
    val navController = rememberNavController()
    // Initialise une activité factice pour la prévisualisation
    val activity = ComponentActivity()
    // Applique le thème personnalisé ChallengeItTheme et appelle le composant LoginScreen
    ChallengeItTheme {
        LoginScreen(navController, activity)
    }
}
