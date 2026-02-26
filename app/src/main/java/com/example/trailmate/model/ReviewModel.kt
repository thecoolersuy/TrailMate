package com.example.trailmate.model

data class ReviewModel(
    var reviewId: String = "",
    val reviewerName: String = "",
    val stars: Int = 0,
    val reviewText: String = ""
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "reviewId" to reviewId,
            "reviewerName" to reviewerName,
            "stars" to stars,
            "reviewText" to reviewText
        )
    }
}