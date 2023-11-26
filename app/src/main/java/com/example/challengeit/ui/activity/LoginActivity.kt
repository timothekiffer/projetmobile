package com.example.challengeit.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.ui.component.LoginScreen
import com.example.challengeit.ui.component.MainNav
import com.example.challengeit.ui.component.RegistrationScreen
import com.example.challengeit.ui.component.WelcomeScreen
import com.example.challengeit.ui.navigation.Screen
import com.example.challengeit.ui.theme.ChallengeItTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeItTheme {
                val activity = this
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.Welcome.route) {
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
fun connexion(username: String, password: String, activity: ComponentActivity) {
    MainActivity.auth.signInWithEmailAndPassword(username, password)
        .addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                Log.d("UserStatus", "Connection success")
                activity.startActivity(Intent(activity, HomeActivity::class.java))
                activity.finish()
            } else {
                Log.d("UserStatus", "Connection failed")
            }
        }
}
fun inscription(email: String, password: String, pseudo:String, activity: ComponentActivity) {
    MainActivity.auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("UserStatus", "createUserWithEmail:success")
                activity.startActivity(Intent(activity, HomeActivity::class.java))
                activity.finish()
            } else {
                // If sign in fails, display a message to the user.
                Log.w("UserStatus", "createUserWithEmail:failure", task.exception)
            }
        }
}
