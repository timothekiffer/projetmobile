package com.example.challengeit.ui.dataclass

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Group(
    var name: String,
    var description: String,
    var isPrivate: Boolean,
) {
    override fun toString(): String {
        return "Group(nom='$name', description='$description')"
    }
}

