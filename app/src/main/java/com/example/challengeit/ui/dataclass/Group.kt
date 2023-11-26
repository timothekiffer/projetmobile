package com.example.challengeit.ui.dataclass

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Group(
    var id: String,
    var name: String,
    var description: String
) {
    override fun toString(): String {
        return "Group(nom='$name', description='$description')"
    }
}

