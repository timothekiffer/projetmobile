package com.example.challengeit.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.challengeit.ui.component.MainNav
import com.example.challengeit.ui.theme.ChallengeItTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeItTheme {
                MainNav(this)
            }
        }
    }
}
fun connexion(username: String, password: String, activity: ComponentActivity) {
    MainActivity.auth.signInWithEmailAndPassword(username, password)
        .addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                Log.d("UserStatus", "Connection success")
                activity.startActivity(Intent(activity, HomeActivity::class.java))
                activity.finish()
            } else {
                Log.d("UserStatus", "Connection failed")
            }
        }
}