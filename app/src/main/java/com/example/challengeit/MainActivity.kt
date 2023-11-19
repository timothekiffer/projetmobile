package com.example.challengeit

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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

class MainActivity : ComponentActivity() {
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
                    NavHost(navController = navController, startDestination = "home"){
                        composable("home"){
                            HomeScreen(navController)
                        }
                        composable("detail"){
                            DetailScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Page d'accueil")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("detail") }) {
            Text(text = "Aller à la page de détail")
        }
    }
}

@Composable
fun DetailScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Page de détail")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = { navController.popBackStack() }) {
            Text(text = "Retour à la page d'accueil")
        }
    }
}



@Composable
fun HomeView(name: String, modifier: Modifier = Modifier) {
    Column(modifier = Modifier
        .padding(10.dp)
        .verticalScroll(rememberScrollState())) {
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
        Surface(shape = MaterialTheme.shapes.small, tonalElevation = 8.dp, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = "Se connecter",
                modifier = Modifier.padding(all = 8.dp),
                style = MaterialTheme.typography.labelLarge
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Surface(shape = MaterialTheme.shapes.small, tonalElevation = 8.dp, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = "S'inscrire",
                modifier = Modifier.padding(all = 8.dp),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

data class Challenge(val name: String, val point: Int)

@Composable
fun ChallengeList(challenges: List<Challenge>, modifier: Modifier) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
    ) {
        items(challenges) { challenge ->
            ChallengeRow(challenge)
        }
    }
}

@Composable
fun ChallengeRow(challenge: Challenge) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "${challenge.name} - ${challenge.point} pts",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeListScreen(challenges: List<Challenge>) {
    ChallengeItTheme {
        Scaffold(
            bottomBar = { Navigation() }
        ) { innerPadding ->
            ChallengeList(challenges, Modifier.padding(innerPadding))
        }
    }
}

@Composable
private fun Navigation(modifier: Modifier = Modifier) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(text = "home")
            },
            selected = true,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text(text = "profile")
            },
            selected = false,
            onClick = {}
        )
    }
}

@Preview()
@Composable
fun ChallengeListScreenPreview() {
    val challenges = listOf(
        Challenge(name = "Faire 300 pas en 1 minute", point = 5),
        Challenge(name = "Prendre un selfie devant la Tour Eiffel", point = 30),
        Challenge(name = "Prendre un bain de minuit", point = 10)
    )

    ChallengeListScreen(challenges = challenges)
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
fun HomeViewPreview() {
    ChallengeItTheme {
        HomeView("Challenge It")
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    ChallengeItTheme {
        HomeScreen(navController)
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    val navController = rememberNavController()
    ChallengeItTheme {
        DetailScreen(navController)
    }
}