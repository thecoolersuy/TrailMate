package com.example.trailmate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trailmate.model.ReviewModel
import com.example.trailmate.repository.ReviewRepo

class ReviewViewModel(val repo: ReviewRepo) : ViewModel() {

    fun addReview(
        model: ReviewModel,
        callback: (Boolean, String) -> Unit
    ) {
        repo.addReview(model, callback)
    }

    fun deleteReview(reviewId: String, callback: (Boolean, String) -> Unit) {
        repo.deleteReview(reviewId, callback)
    }

    fun editReview(
        model: ReviewModel,
        callback: (Boolean, String) -> Unit
    ) {
        repo.editReview(model, callback)
    }

    private val _review = MutableLiveData<ReviewModel?>()
    val review: MutableLiveData<ReviewModel?> get() = _review

    fun getReviewById(reviewId: String) {
        repo.getReviewById(reviewId) { success, message, data ->
            if (success) {
                _review.value = data
            } else {
                _review.value = null
            }
        }
    }

    private val _allReviews = MutableLiveData<List<ReviewModel>?>()
    val allReviews: MutableLiveData<List<ReviewModel>?> get() = _allReviews

    fun getAllReviews() {
        repo.getAllReviews { success, message, data ->
            if (success) {
                _allReviews.value = data
            } else {
                _allReviews.value = emptyList()
            }
        }
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading: MutableLiveData<Boolean> get() = _loading
}