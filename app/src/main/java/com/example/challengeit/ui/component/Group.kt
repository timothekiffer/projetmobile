package com.example.challengeit.ui.component

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
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.ui.dataclass.Challenge
import com.example.challengeit.ui.navigation.Screen
import com.example.challengeit.ui.theme.ChallengeItTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupScreen(challenges: List<Challenge>, navController: NavHostController, id: String) {
    ChallengeItTheme {
        Scaffold(
            bottomBar = { Navigation(navController = navController) }
        ) { innerPadding ->
            GroupBody(challenges, navController, Modifier.padding(innerPadding), id)
        }
    }
}

@Composable
fun GroupBody(challenges: List<Challenge>, navController: NavHostController, modifier: Modifier, id: String) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = id,
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Défis en cours :",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(challenges) { challenge ->
                ChallengeItem(challenge, navController)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate(Screen.Leaderboard.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
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
        Button(
            onClick = { navController.navigate(Screen.Challenge.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "${challenge.name} - ${challenge.point} pts")
        }
    }
}

@Preview()
@Composable
fun GroupScreenPreview() {
    val navController = rememberNavController()
    val challenges = listOf(
        Challenge(name = "Faire 300 pas en 1 minute", description = "Pour valider le défi, tu dois faire 1000 pas en 1 minute, cela devra être filmé et uploadé sur l’appli", point = 5),
        Challenge(name = "Prendre un selfie devant la Tour Eiffel", description = "", point = 30),
        Challenge(name = "Prendre un bain de minuit", description = "", point = 10),
        Challenge(name = "Danser la macarena sur une place publique", description = "", point = 20)
    )
    val id = "Groupe UQAC"
    ChallengeItTheme {
        GroupScreen(challenges, navController, id)
    }
}