package com.example.challengeit.ui.dataclass

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class Group(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var code: String = "",
    @PropertyName("private") val isPrivate: Boolean = false,
    var users: List<String> = listOf()
)
