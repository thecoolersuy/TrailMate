package com.example.trailmate.repository

import com.example.trailmate.model.ReviewModel

interface ReviewRepo {

    fun addReview(
        model: ReviewModel,
        callback: (Boolean, String) -> Unit
    )

    fun deleteReview(
        reviewId: String,
        callback: (Boolean, String) -> Unit
    )

    fun editReview(
        model: ReviewModel,
        callback: (Boolean, String) -> Unit
    )

    fun getAllReviews(
        callback: (Boolean, String, List<ReviewModel>?) -> Unit
    )

    fun getReviewById(
        reviewId: String,
        callback: (Boolean, String, ReviewModel?) -> Unit
    )
}