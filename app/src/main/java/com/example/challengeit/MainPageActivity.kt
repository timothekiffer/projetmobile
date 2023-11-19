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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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

class MainPageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeItTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.MainPage.route) {
                    composable(Screen.MainPage.route){
                        val groups = listOf(
                            Group(name = "Groupe UQAC"),
                            Group(name = "Groupe 2"),
                            Group(name = "Groupe 3")
                        )
                        MainPageScreen(navController, groups)
                    }
                    composable(Screen.Home.route) {
                        HomeScreen("Challenge It", navController)
                    }
                }
            }
        }
    }
}

data class Group(val name: String)

@Composable
fun MainPageScreen(navController: NavHostController, groups: List<Group>) {
    Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text(
            text = "Mes groupes de défis",
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
            Button(onClick = { navController.navigate(Screen.Home.route) }) {
                Text(text = "Ajoute un groupe ici")
            }
        }
    }
}

@Composable
fun GroupItem(group: Group, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate(Screen.Group.route) }) {
            Text(text = group.name)
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview
@Composable
fun MainPageScreenPreview() {
    val navController = rememberNavController()
    val groups = listOf(
        Group(name = "Groupe UQAC"),
        Group(name = "Groupe 2"),
        Group(name = "Groupe 3")
    )
    ChallengeItTheme {
        MainPageScreen(navController, groups)
    }
}
