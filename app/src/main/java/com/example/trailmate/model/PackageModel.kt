package com.example.trailmate.model

data class PackageModel(
    var packageId : String = "",
    val packageName : String ="",
    val packageDuration : Int = 0,
    val packageCapacity: Int = 0,
    val packageDifficulty : String ="",
    val packagePrice : Double = 0.0,
    var image: String = "",
){
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "packageId" to packageId,
            "packageName" to packageName,
            "packageDuration" to packageDuration,
            "packageCapacity" to packageCapacity,
            "packageDifficulty" to packageDifficulty,
            "packagePrice" to packagePrice,
            "image" to image
        )
    }
}
