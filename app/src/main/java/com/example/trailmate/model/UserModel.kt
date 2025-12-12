package com.example.trailmate.model

data class UserModel(
    val userId: String = "",
    val fullName : String ="",
    val email: String ="",
    val password: String ="",
){
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "userId" to userId,
            "fullName" to fullName,
            "email" to email,
            "password" to password
        )

    }
}

