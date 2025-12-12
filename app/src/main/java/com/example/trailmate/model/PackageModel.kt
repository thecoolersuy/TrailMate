package com.example.trailmate.model

data class PackageModel(
    val packageId : String = "",
    val userId : String ="",
    val packageImage : String = "",
    val packageName : String ="",
    val packageDuration : String="",
    val packageCapacity: String ="",
    val packageDifficulty : String ="",
    val packagePrice : String =""
){
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "packageId" to packageId,
            "userId" to userId,
            "packageImage" to packageImage,
            "packageName" to packageName,
            "packageDuration" to packageDuration,
            "packageCapacity" to packageCapacity,
            "packageDifficulty" to packageDifficulty,
            "packagePrice" to packagePrice
        )
    }
}
