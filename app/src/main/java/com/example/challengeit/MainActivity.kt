package com.example.challengeit

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
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
                    HomeView("Challenge It")
                }
            }
        }
    }
}

@Composable
fun HomeView(name: String, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.padding(10.dp)) {
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
            modifier = Modifier.size(400.dp).border(5.dp, MaterialTheme.colorScheme.secondary)
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