// Déclaration du package et des importations nécessaires
package com.example.challengeit.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.challengeit.ui.theme.ChallengeItTheme
import com.google.firebase.auth.FirebaseAuth

// Déclaration de la classe MainActivity qui étend ComponentActivity
class MainActivity : ComponentActivity() {

    // Companion object pour stocker des propriétés et des méthodes statiques
    companion object {
        // Variable statique pour suivre l'état d'authentification
        var isAuthentified = 0

        // Objet FirebaseAuth pour gérer l'authentification avec Firebase
        lateinit var auth: FirebaseAuth
    }

    // Fonction appelée lors de la création de l'activité
    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialisation de l'objet FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Récupération de l'utilisateur actuellement connecté
        val user = auth.currentUser

        // Appel du constructeur de la classe mère
        super.onCreate(savedInstanceState)

        // Configuration du contenu de l'activité en utilisant Jetpack Compose
        setContent {
            // Utilisation du thème personnalisé ChallengeItTheme
            ChallengeItTheme {
                // Création d'une surface (zone rectangulaire) qui occupe toute la taille disponible
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    // Utilisation de la couleur d'arrière-plan définie dans le thème MaterialTheme
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Création d'une intention pour démarrer l'activité LoginActivity
                    intent = Intent(this, LoginActivity::class.java)
                    // Démarrage de l'activité LoginActivity
                    startActivity(intent)
                    // Fermeture de l'activité en cours (MainActivity)
                    finish()
                }
            }
        }
    }
}
