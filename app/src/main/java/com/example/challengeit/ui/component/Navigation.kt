// Déclaration du package et des importations nécessaires
package com.example.challengeit.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.challengeit.ui.navigation.Screen

// Composant représentant la barre de navigation inférieure
@Composable
fun Navigation(modifier: Modifier = Modifier, navController: NavHostController) {
    // Utilise le composant NavigationBar pour créer la barre de navigation
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        // Item de navigation pour l'écran principal (Home)
        NavigationBarItem(
            icon = {
                // Utilise l'icône Home
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                // Texte "home" à côté de l'icône
                Text(text = "home")
            },
            // Indique si cet élément est sélectionné (actif)
            selected = true,
            // Action à effectuer lors du clic (naviguer vers l'écran principal)
            onClick = { navController.navigate(Screen.MainPage.route) }
        )

        // Item de navigation pour l'écran de défi (Add)
        NavigationBarItem(
            icon = {
                // Utilise l'icône Add
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            },
            label = {
                // Texte "Défier" à côté de l'icône
                Text(text = "Défier")
            },
            selected = false, // Non sélectionné
            onClick = {} // Action vide, car la navigation pour cet item n'est pas définie
        )

        // Item de navigation pour l'écran de classement (List)
        NavigationBarItem(
            icon = {
                // Utilise l'icône List
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = null
                )
            },
            label = {
                // Texte "classement" à côté de l'icône
                Text(text = "classement")
            },
            selected = false,
            // Action à effectuer lors du clic (naviguer vers l'écran de classement)
            onClick = { navController.navigate(Screen.Leaderboard.route) }
        )
    }
}
