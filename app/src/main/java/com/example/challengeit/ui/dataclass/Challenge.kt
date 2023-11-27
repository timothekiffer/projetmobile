package com.example.challengeit.ui.dataclass

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Challenge(
    val name: String,
    val description: String,
    val point: Int
) {
    constructor() : this("", "", 0)
}