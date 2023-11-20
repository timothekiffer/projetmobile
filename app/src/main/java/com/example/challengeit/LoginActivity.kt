package com.example.challengeit

import android.content.res.Configuration
import android.os.Bundle
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
import com.example.challengeit.ui.theme.ChallengeItTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    composable(Screen.Group.route) {
                        val challenges = listOf(
                            Challenge(id = 1, name = "Faire 300 pas en 1 minute", description = "Pour valider le défi, tu dois faire 1000 pas en 1 minute, cela devra être filmé et uploadé sur l’appli", point = 5),
                            Challenge(id = 2, name = "Prendre un selfie devant la Tour Eiffel", description = "", point = 30),
                            Challenge(id = 3, name = "Prendre un bain de minuit", description = "", point = 10),
                            Challenge(id = 4, name = "Danser la macarena sur une place publique", description = "", point = 20)
                        )
                        GroupScreen(challenges, navController)
                    }
                    composable(Screen.JoinGroup.route) {
                        JoinGroupScreen(navController)
                    }
                    composable(Screen.Challenge.route) {
                        val challenge = Challenge(id = 1, name = "Faire 300 pas en 1 minute", description = "Pour valider le défi, tu dois faire 1000 pas en 1 minute, cela devra être filmé et uploadé sur l’appli", point = 5)
                        ChallengeScreen(challenge, navController)
                    }
                    composable(Screen.PrivateGroup.route) {
                        PrivateGroupScreen(navController)
                    }
                    composable(Screen.PublicGroup.route) {
                        val groups = listOf(
                            Group(name = "Groupe France"),
                            Group(name = "Groupe Canada"),
                            Group(name = "Groupe 3")
                        )
                        PublicGroupScreen(groups, navController)
                    }
                    composable(Screen.NewGroup.route) {
                        NewGroupScreen(navController)
                    }
                    composable(Screen.Leaderboard.route) {
                        val users = listOf(
                            User(id = 1, name = "Timothé", point = 150),
                            User(id = 2, name = "Alexandre", point = 89),
                            User(id = 3, name = "Romain", point = 18)
                        )
                        LeaderboardScreen(users, navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
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
            value = "login",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Mot de Passe")
        TextField(
            value = "password",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Screen.MainPage.route) }) {
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