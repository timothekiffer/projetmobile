package com.example.challengeit.ui.component

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.challengeit.ui.activity.inscription

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(navController: NavHostController, activity: ComponentActivity) {
    // Variables d'état pour stocker les informations de l'utilisateur
    var email by remember { mutableStateOf("") }
    var pseudo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
        Text(text = "Dis nous ton âge")
        TextField(
            value = "",
            onValueChange = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Champ pour le pseudo
        Text(text = "Pseudo")
        TextField(
            value = pseudo,
            onValueChange = { pseudo = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Champ pour l'email
        Text(text = "Email")
        TextField(
            value = email,
            onValueChange = { email = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Champ pour le mot de passe
        Text(text = "Mot de passe")
        TextField(
            value = password,
            onValueChange = { password = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton de validation avec l'appel à la fonction "inscription"
        Button(
            onClick = { inscription(email, password, pseudo, activity) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Valider")
        }
    }
}
