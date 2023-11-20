package com.example.challengeit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.ui.theme.ChallengeItTheme

class LeaderboardActivity : ComponentActivity() {
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
                    NavHost(navController = navController, startDestination = Screen.Leaderboard.route){
                        composable(Screen.Leaderboard.route) {
                            val users = listOf(
                                User(id = 1, name = "Timothé", point = 150),
                                User(id = 2, name = "Alexandre", point = 89),
                                User(id = 3, name = "Romain", point = 18)
                            )
                            LeaderboardScreen(users, navController)
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
                    }
                }
            }
        }
    }
}

data class User(val id: Int, val name: String, val point: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(users: List<User>, navController: NavHostController) {
    ChallengeItTheme {
        Scaffold(
            bottomBar = { Navigation(navController = navController) }
        ) { innerPadding ->
            LeaderboardBody(users, navController, Modifier.padding(innerPadding))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardBody(users: List<User>, navController: NavHostController, modifier: Modifier) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Classement",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("ID", fontWeight = FontWeight.Bold)
                    Text("Nom", fontWeight = FontWeight.Bold)
                    Text("Points", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
            items(users) {user ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.onSurface, shape = MaterialTheme.shapes.small),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(user.id.toString())
                    Text(user.name)
                    Text(user.point.toString())
                }
            }
        }

        Button(
            onClick = { navController.navigate(Screen.Group.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Retour aux défis")
        }
    }
}

@Preview()
@Composable
fun LeaderboardScreenPreview() {
    val navController = rememberNavController()
    val users = listOf<User>(
        User(id = 1, name = "Timothé", point = 150),
        User(id = 2, name = "Alexandre", point = 89),
        User(id = 3, name = "Romain", point = 18)
    )
    ChallengeItTheme {
        LeaderboardScreen(users, navController)
    }
}