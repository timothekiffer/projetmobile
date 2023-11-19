package com.example.challengeit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.ui.theme.ChallengeItTheme

class GroupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeItTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.Group.route) {
                        composable(Screen.Group.route) {
                            val challenges = listOf(
                                Challenge(id = 1, name = "Faire 300 pas en 1 minute", description = "Pour valider le défi, tu dois faire 1000 pas en 1 minute, cela devra être filmé et uploadé sur l’appli", point = 5),
                                Challenge(id = 2, name = "Prendre un selfie devant la Tour Eiffel", description = "", point = 30),
                                Challenge(id = 3, name = "Prendre un bain de minuit", description = "", point = 10),
                                Challenge(id = 4, name = "Danser la macarena sur une place publique", description = "", point = 20)
                            )
                            GroupScreen(challenges, navController)
                        }
                    }
                }
            }
        }
    }
}

data class Challenge(val id: Int, val name: String, val description: String, val point: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupScreen(challenges: List<Challenge>, navController: NavHostController) {
    ChallengeItTheme {
        Scaffold(
            bottomBar = { Navigation() }
        ) { innerPadding ->
            GroupBody(challenges, navController, Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun GroupBody(challenges: List<Challenge>, navController: NavHostController, modifier: Modifier) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Groupe UQAC",
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Défis en cours :",
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(challenges) { challenge ->
                ChallengeItem(challenge, navController)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Screen.Leaderboard.route) }) {
            Text(text = "Classement")
        }
    }
}

@Composable
fun ChallengeItem(challenge: Challenge, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate(Screen.Challenge.route) }) {
            Text(text = "${challenge.name} - ${challenge.point} pts")
        }
    }
}

@Preview()
@Composable
fun GroupScreenPreview() {
    val navController = rememberNavController()
    val challenges = listOf(
        Challenge(id = 1, name = "Faire 300 pas en 1 minute", description = "Pour valider le défi, tu dois faire 1000 pas en 1 minute, cela devra être filmé et uploadé sur l’appli", point = 5),
        Challenge(id = 2, name = "Prendre un selfie devant la Tour Eiffel", description = "", point = 30),
        Challenge(id = 3, name = "Prendre un bain de minuit", description = "", point = 10),
        Challenge(id = 4, name = "Danser la macarena sur une place publique", description = "", point = 20)
    )
    ChallengeItTheme {
        GroupScreen(challenges, navController)
    }
}