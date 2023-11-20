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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

class JoinGroupActivity : ComponentActivity() {
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
                    NavHost(navController = navController, startDestination = Screen.JoinGroup.route){
                        composable(Screen.JoinGroup.route) {
                            JoinGroupScreen(navController)
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
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinGroupScreen(navController: NavHostController) {
    ChallengeItTheme {
        Scaffold(
            bottomBar = { Navigation() }
        ) { innerPadding ->
            JoinGroupBody(navController, Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun JoinGroupBody(navController: NavHostController, modifier: Modifier) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Tu veux..",
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate(Screen.PrivateGroup.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Rejoindre un groupe privé")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate(Screen.PublicGroup.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Rejoindre un groupe public")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate(Screen.NewGroup.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Créer un groupe")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Retour")
            }
        }
    }
}

@Preview()
@Composable
fun JoinGroupScreenPreview() {
    val navController = rememberNavController()
    ChallengeItTheme {
        JoinGroupScreen(navController)
    }
}