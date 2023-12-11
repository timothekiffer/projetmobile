package com.example.challengeit.ui.dataclass

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Proof (
    var id: String = "",
    var challenge: String = "",
    var user: String = "",
    val valid: Boolean = false
)
