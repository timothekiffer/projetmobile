// Déclaration du package et des importations nécessaires
package com.example.challengeit.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

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
                Toast.makeText(activity, "Mauvais identifiant ou mot de passe ! Veuillez réessayer", Toast.LENGTH_SHORT).show()
            }

        }
}

// Fonction d'inscription avec Firebase
fun inscription(email: String, password: String, pseudo: String, activity: ComponentActivity) {
    val auth = FirebaseAuth.getInstance()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                // Inscription réussie, redirection vers HomeActivity
                Log.d("UserStatus", "createUserWithEmail:success")
                activity.startActivity(Intent(activity, HomeActivity::class.java))
                activity.finish()

                // Une fois l'utilisateur créé avec succès, met à jour le profil avec le pseudo
                val user = auth.currentUser

                val profileUpdates = userProfileChangeRequest {
                    displayName = pseudo
                }

                user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            // Vérifie que le displayName a bien été mis à jour
                            val updatedUser = auth.currentUser
                            Log.d("ISSOU", "User profile updated. DisplayName: ${updatedUser?.displayName}")

                            // Ajouter une nouvelle collection "user" avec le displayName
                            addNewUserCollection(updatedUser?.uid, pseudo)
                        } else {
                            // Échec de la mise à jour du profil, affichage d'un message d'erreur
                            Log.w("UserStatus", "Échec de la mise à jour du profil", updateTask.exception)
                        }
                    }
            } else {
                Toast.makeText(activity, "Echec de l'inscription ! Veuillez réessayer", Toast.LENGTH_SHORT).show()

                // Échec de l'inscription, affichage d'un message d'erreur
                Log.w("UserStatus", "createUserWithEmail:failure", task.exception)
            }
        }
}

fun addNewUserCollection(userId: String?, displayName: String) {
    if (userId != null) {
        try {
            // Obtenir une instance de la base de données Firestore
            val firestore = FirebaseFirestore.getInstance()

            // Créer une nouvelle collection "users" avec le document correspondant à l'utilisateur
            firestore.collection("user").document(userId)
                .set(mapOf("displayName" to displayName))
                .addOnSuccessListener {
                    Log.d("Firestore", "Nouvelle collection ajoutée pour l'utilisateur: $userId")
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Erreur lors de l'ajout de la nouvelle collection", e)
                }
        } catch (e: Exception) {
            Log.e("Firestore", "Erreur lors de la création de la nouvelle collection", e)
        }
    }
}