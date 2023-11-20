package com.example.challengeit

import android.content.ContentValues.TAG
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.MainActivity.Companion.auth
import com.example.challengeit.ui.theme.ChallengeItTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val currentUser = auth.currentUser

        setContent {
            ChallengeItTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.Login.route) {
                    composable(Screen.Login.route){
                        LoginScreen(navController)
                    }
                    composable(Screen.Registration.route){
                        RegistrationScreen(navController)
                    }
                    composable(Screen.Home.route) {
                        HomeScreen("Challenge It", navController)
                    }
                    composable(Screen.MainPage.route) {
                        val groups = listOf(
                            Group(name = "Groupe UQAC"),
                            Group(name = "Groupe 2"),
                            Group(name = "Groupe 3")
                        )
                        MainPageScreen(groups, navController)
                    }
                }
            }
        }
    }
    private fun signIn(email: String, password: String, param: (Any) -> Unit) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val currentUser = auth.currentUser
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginResult by remember { mutableStateOf(false) } // Nouvelle propriété pour stocker le résultat de la connexion

    Column(modifier = Modifier
        .padding(10.dp)
        .verticalScroll(rememberScrollState())) {
        Text(
            text = "Connecte Toi",
            style = MaterialTheme.typography.labelLarge

        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Adresse email")
        TextField(
            value = email,
            onValueChange = { email = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Mot de Passe")
        TextField(
            value = password,
            onValueChange = { password = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { signIn(email, password){
            val user =auth.currentUser

            if (user != null) {
                Log.d(user.toString(), "signInWithEmail1:success")
                // User is signed in
                navController.navigate(Screen.Home.route)
            } else {
                Log.d(user.toString(), "signInWithEmail1:echec")
                // ERREUR

            }
        }
        }) {
            Text(text = "Connexion")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(navController: NavHostController) {
    Column(modifier = Modifier
        .padding(10.dp)
        .verticalScroll(rememberScrollState())) {
        Text(
            text = "Inscris Toi",
            style = MaterialTheme.typography.labelLarge

        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Dis nous ton âge")
        TextField(
            value = "",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Pseudo")
        TextField(
            value = "",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Identifiant")
        TextField(
            value = "",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Mot de passe")
        TextField(
            value = "",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Screen.Home.route) }) {
            Text(text = "Valider")
        }
    }
}

@Composable
fun HomeScreen(name: String, navController: NavHostController, modifier: Modifier = Modifier) {
    Column(modifier = Modifier
        .padding(10.dp)
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenue sur $name, la plateforme de défi !",
            style = TextStyle(fontWeight = FontWeight.Bold),
            color = Color(0,0,0),
            modifier = modifier
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Ici, vous pouvez participer à une variété de défis passionnants et enrichissants." +
                    " Non seulement vous pouvez apprendre et grandir, mais vous pouvez aussi gagner des points et concourir avec d'autres participants.",
            color = MaterialTheme.colorScheme.secondaryContainer,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier
        )
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(R.drawable.eclair),
            contentDescription = "Image d'accueil",
            modifier = Modifier
                .size(400.dp)
                .border(5.dp, MaterialTheme.colorScheme.secondary)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.navigate(Screen.Login.route) }) {
            Text(
                text = "Se connecter",
                modifier = Modifier.padding(all = 8.dp),
                style = MaterialTheme.typography.labelLarge
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.navigate(Screen.Registration.route) }) {
            Text(
                text = "S'inscrire",
                modifier = Modifier.padding(all = 8.dp),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    ChallengeItTheme {
        LoginScreen(navController)
    }
}

@Preview
@Composable
fun RegistrationScreenPreview() {
    val navController = rememberNavController()
    ChallengeItTheme {
        RegistrationScreen(navController)
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = false,
    name = "Dark Mode"
)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    ChallengeItTheme {
        HomeScreen("Challenge It", navController)
    }
}

}