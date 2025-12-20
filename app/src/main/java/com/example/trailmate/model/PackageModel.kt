package com.example.trailmate.model

data class PackageModel(
    var packageId : String = "",
    val packageName : String ="",
    val packageDuration : String="",
    val packageCapacity: String ="",
    val packageDifficulty : String ="",
    val packagePrice : String =""
){
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "packageId" to packageId,
            "packageName" to packageName,
            "packageDuration" to packageDuration,
            "packageCapacity" to packageCapacity,
            "packageDifficulty" to packageDifficulty,
            "packagePrice" to packagePrice,
        )
    }
}
