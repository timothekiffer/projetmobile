package com.example.challengeit.ui.component

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.ui.activity.inscription
import com.example.challengeit.ui.theme.ChallengeItTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun RegistrationScreen(navController: NavHostController, activity: ComponentActivity) {
    // Variables d'état pour stocker les informations de l'utilisateur
    var email by remember { mutableStateOf("") }
    var pseudo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var age by remember { mutableStateOf(0) }

    // Obtient le contexte local et le contrôleur de clavier
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // Utilisation du composant Column pour organiser les éléments de manière verticale
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Titre de la page
        Text(
            text = "Inscris-toi",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Champ pour l'âge (ici, il n'est pas lié à une variable d'état)
        Text(text = "Tu dois être majeur pour participer")
        TextField(
            value = age.toString(),
            onValueChange = {
                // Vérifie si la nouvelle valeur est un nombre positif
                val newValue = it.toIntOrNull()
                if ((newValue != null) && (newValue >= 0)) {
                    age = newValue
                }
            },
            label = { Text("Âge") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
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

        // Champ pour le pseudo
        Text(text = "Trouve un pseudo stylé")
        TextField(
            value = pseudo,
            onValueChange = { pseudo = it },
            label = { Text("Pseudo") },
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

        // Champ pour l'email
        Text(text = "Il nous faut ton email t'identifier")
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
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

        // Champ pour le mot de passe
        Text(text = "Écris un mot de passe de 8 caractères")
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
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

        // Bouton de validation avec l'appel à la fonction "inscription"
        Button(
            onClick = { inscription(email, password, pseudo, age, activity) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Valider")
        }
    }
}

// Fonction de prévisualisation pour l'écran d'inscription
@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    // Initialise un contrôleur de navigation factice pour la prévisualisation
    val navController = rememberNavController()
    // Initialise une activité factice pour la prévisualisation
    val activity = ComponentActivity()
    // Applique le thème personnalisé ChallengeItTheme et appelle le composant RegistrationScreen
    ChallengeItTheme {
        RegistrationScreen(navController, activity)
    }
}
