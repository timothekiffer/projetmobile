package com.example.challengeit.ui.dataclass

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Challenge(
    var name: String = "",
    var description: String = "",
    var point: Int = 0,
    var group: String = ""
)