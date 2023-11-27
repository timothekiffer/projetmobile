// Déclaration du package et des importations nécessaires
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
import com.example.challengeit.ui.dataclass.Group
import com.example.challengeit.ui.theme.ChallengeItTheme

// Annotation indiquant que cette fonction utilise des fonctionnalités expérimentales de Compose
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicGroupScreen(groups: List<Group>, navController: NavHostController) {
    // Utilise le thème ChallengeIt
    ChallengeItTheme {
        // Utilise le composant Scaffold pour la mise en page de l'écran
        Scaffold(
            bottomBar = { Navigation(navController = navController) }
        ) { innerPadding ->
            // Utilise le composant PublicGroupBody pour la partie centrale de l'écran
            PublicGroupBody(navController, groups, Modifier.padding(innerPadding))
        }
    }
}

// Composant représentant le corps de l'écran de sélection d'un groupe public
@Composable
fun PublicGroupBody(navController: NavHostController, groups: List<Group>, modifier: Modifier) {
    // Utilise le composant Column pour organiser les éléments de manière verticale
    Column(modifier = modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Titre de la page
        Text(
            text = "Rejoins un groupe public",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Utilise le composant LazyColumn pour afficher la liste des groupes publics
        LazyColumn (
            modifier = Modifier.fillMaxWidth()
        ) {
            items(groups) { group ->
                // Utilise le composant GroupItem pour afficher chaque élément de la liste
                GroupItem(group, navController)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton de retour
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { navController.popBackStack() },  // Action de retour
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Retour")
            }
        }
    }
}

// Prévisualisation de l'écran de sélection d'un groupe public
@Preview
@Composable
fun PublicGroupScreenPreview() {
    // Initialise le contrôleur de navigation
    val navController = rememberNavController()

    // Liste fictive de groupes publics pour la prévisualisation
    val groups = listOf(
        Group(name = "Groupe France", description = "",isPrivate = false),
        Group(name = "Groupe Canada", description = "",isPrivate = false),
        Group(name = "Groupe 3", description = "",isPrivate = false)
    )

    // Applique le thème ChallengeIt
    ChallengeItTheme {
        // Affiche l'écran de sélection d'un groupe public dans la prévisualisation
        PublicGroupScreen(groups, navController)
    }
}
