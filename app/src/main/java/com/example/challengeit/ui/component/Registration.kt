package com.example.challengeit.ui.component

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.R
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
    var isPasswordVisible by remember { mutableStateOf(false) }
    // Obtient le contexte local et le contrôleur de clavier
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        // Utilisation de la couleur d'arrière-plan définie dans Color
        color = colorResource(id = R.color.purple) // Utilisez l'ID de la couleur
    ) {
        // Titre de la page
        Text(
            text = "Inscris-toi",
            color = Color.White,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 50.dp)
        )

        // Ajoute un espace vertical
        Spacer(modifier = Modifier.height(20.dp))

        // Utilisation du composant Column pour organiser les éléments de manière verticale
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Champ pour l'âge
            Text(
                text = "Tu dois être majeur pour participer",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = age.toString(),
                onValueChange = {
                    // Vérifie si la nouvelle valeur est un nombre positif
                    val newValue = it.toIntOrNull()
                    if ((newValue != null) && (newValue >= 18)) {
                        age = newValue
                    }
                },
                label = {
                    Text(
                        text = "Âge",
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

            // Ajoute un espace vertical
            Spacer(modifier = Modifier.height(20.dp))

            // Champ pour le pseudo
            Text(
                text = "Trouve un pseudo stylé",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = pseudo,
                onValueChange = { pseudo = it },
                label = {
                    Text(
                        text = "Pseudo",
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

            // Champ pour l'email
            Text(
                text = "Il nous faut ton email",
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

            // Champ pour le mot de passe
            Text(
                text = "Écris un mot de passe sécurisé",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
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
                ),trailingIcon = {
                    IconButton(
                        onClick = {
                            isPasswordVisible = !isPasswordVisible
                        }
                    ) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.Add else Icons.Default.ArrowDropDown,
                            contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password",
                            tint = Color.Black
                        )
                    }
                },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                )

            // Ajoute un espace vertical
            Spacer(modifier = Modifier.height(20.dp))

            // Bouton de validation avec l'appel à la fonction "inscription"
            Button(
                onClick = { inscription(email, password, pseudo, age, activity) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "Valider",
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
                Text(
                    text = "Retour",
                    color = Color.White
                )
            }
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
