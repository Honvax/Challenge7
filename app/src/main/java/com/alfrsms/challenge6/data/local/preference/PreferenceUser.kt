package com.alfrsms.challenge6.data.local.preference

data class PreferenceUser(
    var id: Int? = 0,
    val username: String,
    val email: String,
    val password: String? = "",
    val fullName: String? = "",
    val dateOfBirth: String? = "",
    val address: String? = "",
    val profileImage: String? = "",
    val userLoggedIn: Boolean = false
)