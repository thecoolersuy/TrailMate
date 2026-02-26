package com.example.trailmate.repository

import com.example.trailmate.model.ReviewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ReviewRepoImpl : ReviewRepo {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val ref: DatabaseReference = database.getReference("reviews")

    override fun addReview(
        model: ReviewModel,
        callback: (Boolean, String) -> Unit
    ) {
        val id = ref.push().key.toString()
        model.reviewId = id

        ref.child(id).setValue(model).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Review added successfully")
            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun deleteReview(
        reviewId: String,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(reviewId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Review deleted successfully")
            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun editReview(
        model: ReviewModel,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(model.reviewId).updateChildren(model.toMap()).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Review updated successfully")
            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun getAllReviews(callback: (Boolean, String, List<ReviewModel>?) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val allReviews = mutableListOf<ReviewModel>()
                    for (data in snapshot.children) {
                        val review = data.getValue(ReviewModel::class.java)
                        if (review != null) {
                            allReviews.add(review)
                        }
                    }
                    callback(true, "Reviews fetched successfully", allReviews)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, error.message, emptyList())
            }
        })
    }

    override fun getReviewById(
        reviewId: String,
        callback: (Boolean, String, ReviewModel?) -> Unit
    ) {
        ref.child(reviewId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val review = snapshot.getValue(ReviewModel::class.java)
                    if (review != null) {
                        callback(true, "Review fetched successfully", review)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, error.message, null)
            }
        })
    }
}