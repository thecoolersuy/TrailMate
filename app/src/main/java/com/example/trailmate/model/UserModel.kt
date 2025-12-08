package com.example.trailmate.model

data class UserModel(
    val userId: String = "",
    val email: String ="",
    val password: String ="",
){
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "userId" to userId,
            "email" to email,
            "password" to password
        )

    }
}

