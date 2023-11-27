package com.example.challengeit.ui.dataclass

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class Group(
    var name: String,
    var description: String,
    @PropertyName("private") var isPrivate: Boolean,
) {
    constructor() : this("", "", false)

}

