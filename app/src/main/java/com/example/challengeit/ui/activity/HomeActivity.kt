// Déclaration du package de l'application
package com.example.challengeit.ui.activity

// Importation des bibliothèques nécessaires
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.challengeit.ui.activity.ui.theme.ChallengeItTheme
import com.example.challengeit.ui.component.MainNav

// Déclaration de la classe HomeActivity qui étend ComponentActivity
class HomeActivity : ComponentActivity() {

    // Fonction appelée lors de la création de l'activité
    override fun onCreate(savedInstanceState: Bundle?) {
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
                    // Appel du composant MainNav pour afficher la navigation principale
                    MainNav(null)
                }
            }
        }
    }
}