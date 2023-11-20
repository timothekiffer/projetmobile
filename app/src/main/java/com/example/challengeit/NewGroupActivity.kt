package com.example.challengeit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

class NewGroupActivity : ComponentActivity() {
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
                    NavHost(navController = navController, startDestination = Screen.NewGroup.route){
                        composable(Screen.NewGroup.route) {
                            NewGroupScreen(navController)
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewGroupScreen(navController: NavHostController) {
    ChallengeItTheme {
        Scaffold(
            bottomBar = { Navigation() }
        ) { innerPadding ->
            NewGroupBody(navController, Modifier.padding(innerPadding))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewGroupBody(navController: NavHostController, modifier: Modifier) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Création du groupe",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Nom du groupe")
        TextField(
            value = "Groupe UQAC",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate(Screen.MainPage.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Créer")
        }
    }
}

@Preview()
@Composable
fun NewGroupScreenPreview() {
    val navController = rememberNavController()
    ChallengeItTheme {
        NewGroupScreen(navController)
    }
}