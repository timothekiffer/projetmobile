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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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
                    //Greeting("Android")
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
            bottomBar = { Navigation() }
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
            style = MaterialTheme.typography.labelLarge
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

        Button(onClick = { navController.navigate(Screen.Group.route) }) {
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