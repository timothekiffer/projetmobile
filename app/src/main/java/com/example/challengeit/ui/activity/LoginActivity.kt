package com.example.challengeit.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.challengeit.ui.component.MainNav
import com.example.challengeit.ui.theme.ChallengeItTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeItTheme {
                MainNav()
            }
        }
    }
}