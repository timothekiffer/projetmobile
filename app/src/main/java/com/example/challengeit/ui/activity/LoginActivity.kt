// Déclaration du package et des importations nécessaires
package com.example.challengeit.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.ui.activity.MainActivity.Companion.auth
import com.example.challengeit.ui.component.LoginScreen
import com.example.challengeit.ui.component.RegistrationScreen
import com.example.challengeit.ui.component.WelcomeScreen
import com.example.challengeit.ui.navigation.Screen
import com.example.challengeit.ui.theme.ChallengeItTheme
import com.google.firebase.auth.FirebaseAuth

// Déclaration de la classe LoginActivity qui étend ComponentActivity
class LoginActivity : ComponentActivity() {

    // Fonction appelée lors de la création de l'activité
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuration du contenu de l'activité en utilisant Jetpack Compose
        setContent {
            // Utilisation du thème personnalisé ChallengeItTheme
            ChallengeItTheme {
                // Récupération de l'activité et création du contrôleur de navigation
                val activity = this
                val navController = rememberNavController()

                // Définition du point d'entrée de la navigation (NavHost)
                NavHost(navController = navController, startDestination = Screen.Welcome.route) {
                    // Configuration des écrans de l'application avec Compose
                    composable(Screen.Welcome.route) {
                        WelcomeScreen("Challenge It", navController)
                    }
                    composable(Screen.Login.route) {
                        LoginScreen(navController, activity)
                    }
                    composable(Screen.Registration.route) {
                        RegistrationScreen(navController, activity)
                    }
                }
            }
        }
    }
}

// Fonction de connexion avec Firebase
fun connexion(username: String, password: String, activity: ComponentActivity) {
    auth.signInWithEmailAndPassword(username, password)
        .addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                // Connexion réussie, redirection vers HomeActivity
                Log.d("UserStatus", "Connection success")
                var test = FirebaseAuth.getInstance().currentUser?.uid
                Log.d("Test", test.toString())
                activity.startActivity(Intent(activity, HomeActivity::class.java))
                activity.finish()
            } else {
                // Échec de la connexion
                Log.d("UserStatus", "Connection failed")
            }
        }
}

// Fonction d'inscription avec Firebase
fun inscription(email: String, password: String, pseudo: String, age: Int, activity: ComponentActivity) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                // Inscription réussie, redirection vers HomeActivity
                Log.d("UserStatus", "createUserWithEmail:success")
                activity.startActivity(Intent(activity, HomeActivity::class.java))
                activity.finish()
            } else {
                // Échec de l'inscription, affichage d'un message d'erreur
                Log.w("UserStatus", "createUserWithEmail:failure", task.exception)
            }
        }
}
