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

class PublicGroupActivity : ComponentActivity() {
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
                    NavHost(navController = navController, startDestination = Screen.PublicGroup.route){
                        composable(Screen.PublicGroup.route) {
                            val groups = listOf(
                                Group(name = "Groupe France"),
                                Group(name = "Groupe Canada"),
                                Group(name = "Groupe 3")
                            )
                            PublicGroupScreen(groups, navController)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicGroupScreen(groups: List<Group>, navController: NavHostController) {
    ChallengeItTheme {
        Scaffold(
            bottomBar = { Navigation() }
        ) { innerPadding ->
            PublicGroupBody(navController, groups, Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun PublicGroupBody(navController: NavHostController, groups: List<Group>, modifier: Modifier) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Rejoins un groupe public",
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn (
            modifier = Modifier.fillMaxWidth()
        ){
            items(groups) { group ->
                GroupItem(group, navController)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = { navController.popBackStack() }) {
                Text(text = "Retour")
            }
        }
    }
}

@Preview
@Composable
fun PublicGroupScreenPreview() {
    val navController = rememberNavController()
    val groups = listOf(
        Group(name = "Groupe France"),
        Group(name = "Groupe Canada"),
        Group(name = "Groupe 3")
    )
    ChallengeItTheme {
        PublicGroupScreen(groups, navController)
    }
}