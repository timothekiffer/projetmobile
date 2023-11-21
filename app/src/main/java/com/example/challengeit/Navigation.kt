package com.example.challengeit

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

@Composable
fun Navigation(modifier: Modifier = Modifier, navController: NavHostController) {
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
            onClick = { navController.navigate(Screen.MainPage.route) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            },
            label = {
                Text(text = "Défier")
            },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = null
                )
            },
            label = {
                Text(text = "classement")
            },
            selected = false,
            onClick = { navController.navigate(Screen.Leaderboard.route) }
        )
    }
}
