package com.example.trailmate.model

data class UserModel(
    val userId: String = "",
    val fullName : String ="",
    val location : String ="",
    val email: String ="",
    val password: String ="",
){
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "userId" to userId,
            "fullName" to fullName,
            "location" to location,
            "email" to email,
            "password" to password
        )

    }
}

