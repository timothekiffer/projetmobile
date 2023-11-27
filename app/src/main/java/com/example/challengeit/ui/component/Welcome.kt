package com.example.challengeit.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.challengeit.R
import com.example.challengeit.ui.navigation.Screen
import com.example.challengeit.ui.theme.ChallengeItTheme

@Composable
fun WelcomeScreen(name: String, navController: NavHostController, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Titre de bienvenue avec le nom de la plateforme
        Text(
            text = "Bienvenue sur $name, la plateforme de défi !",
            style = TextStyle(fontWeight = FontWeight.Bold),
            color = Color(0, 0, 0),  // Couleur personnalisée (noir)
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Texte d'introduction avec des informations sur la plateforme
        Text(
            text = "Ici, vous pouvez participer à une variété de défis passionnants et enrichissants." +
                    " Non seulement vous pouvez apprendre et grandir, mais vous pouvez aussi gagner des points et concourir avec d'autres participants.",
            color = MaterialTheme.colorScheme.secondaryContainer,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Image d'accueil
        Image(
            painter = painterResource(R.drawable.eclair),
            contentDescription = "Image d'accueil",
            modifier = Modifier
                .size(400.dp)
                .border(5.dp, MaterialTheme.colorScheme.secondary)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Bouton de connexion
        Button(
            onClick = { navController.navigate(Screen.Login.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Se connecter",
                modifier = Modifier.padding(all = 8.dp),
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Bouton d'inscription
        Button(
            onClick = { navController.navigate(Screen.Registration.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.medium
        ) {
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
fun WelcomeScreenPreview() {
    val navController = rememberNavController()
    ChallengeItTheme {
        WelcomeScreen("Challenge It", navController)
    }
}
