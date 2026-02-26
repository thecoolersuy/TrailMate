package com.example.trailmate.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.trailmate.model.ReviewModel
import com.example.trailmate.repository.ReviewRepo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ReviewViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Test 1: Add review success
    @Test
    fun addReview_success_test() {
        val repo = mock<ReviewRepo>()
        val viewModel = ReviewViewModel(repo)

        val reviewModel = ReviewModel(
            reviewId = "",
            reviewerName = "John Doe",
            stars = 5,
            reviewText = "Amazing trek experience!"
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Review added successfully")
            null
        }.`when`(repo).addReview(eq(reviewModel), any())

        var successResult = false
        var messageResult = ""

        viewModel.addReview(reviewModel) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Review added successfully", messageResult)
        verify(repo).addReview(eq(reviewModel), any())
    }

    // Test 2: Add review failure
    @Test
    fun addReview_failure_test() {
        val repo = mock<ReviewRepo>()
        val viewModel = ReviewViewModel(repo)

        val reviewModel = ReviewModel(
            reviewId = "",
            reviewerName = "Jane Doe",
            stars = 3,
            reviewText = "It was okay."
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(false, "Failed to add review")
            null
        }.`when`(repo).addReview(eq(reviewModel), any())

        var successResult = true
        var messageResult = ""

        viewModel.addReview(reviewModel) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertFalse(successResult)
        assertEquals("Failed to add review", messageResult)
        verify(repo).addReview(eq(reviewModel), any())
    }

    // Test 3: Delete review success
    @Test
    fun deleteReview_success_test() {
        val repo = mock<ReviewRepo>()
        val viewModel = ReviewViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Review deleted successfully")
            null
        }.`when`(repo).deleteReview(eq("review123"), any())

        var successResult = false
        var messageResult = ""

        viewModel.deleteReview("review123") { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Review deleted successfully", messageResult)
        verify(repo).deleteReview(eq("review123"), any())
    }

    // Test 4: Delete review failure
    @Test
    fun deleteReview_failure_test() {
        val repo = mock<ReviewRepo>()
        val viewModel = ReviewViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(false, "Review not found")
            null
        }.`when`(repo).deleteReview(eq("nonexistent"), any())

        var successResult = true
        var messageResult = ""

        viewModel.deleteReview("nonexistent") { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertFalse(successResult)
        assertEquals("Review not found", messageResult)
        verify(repo).deleteReview(eq("nonexistent"), any())
    }

    // Test 5: Edit review success
    @Test
    fun editReview_success_test() {
        val repo = mock<ReviewRepo>()
        val viewModel = ReviewViewModel(repo)

        val updatedReview = ReviewModel(
            reviewId = "review123",
            reviewerName = "John Doe",
            stars = 4,
            reviewText = "Updated: Really great experience overall."
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Review updated successfully")
            null
        }.`when`(repo).editReview(eq(updatedReview), any())

        var successResult = false
        var messageResult = ""

        viewModel.editReview(updatedReview) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Review updated successfully", messageResult)
        verify(repo).editReview(eq(updatedReview), any())
    }

    // Test 6: Get review by id success
    @Test
    fun getReviewById_success_test() {
        val repo = mock<ReviewRepo>()
        val viewModel = ReviewViewModel(repo)

        val mockReview = ReviewModel(
            reviewId = "review123",
            reviewerName = "Alice",
            stars = 5,
            reviewText = "Best trail guide ever!"
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, ReviewModel?) -> Unit>(1)
            callback(true, "Review fetched successfully", mockReview)
            null
        }.`when`(repo).getReviewById(eq("review123"), any())

        viewModel.getReviewById("review123")

        Thread.sleep(100)

        assertNotNull(viewModel.review.value)
        assertEquals("Alice", viewModel.review.value?.reviewerName)
        assertEquals(5, viewModel.review.value?.stars)
        assertEquals("Best trail guide ever!", viewModel.review.value?.reviewText)
        verify(repo).getReviewById(eq("review123"), any())
    }

    // Test 7: Get review by id failure
    @Test
    fun getReviewById_failure_test() {
        val repo = mock<ReviewRepo>()
        val viewModel = ReviewViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, ReviewModel?) -> Unit>(1)
            callback(false, "Review not found", null)
            null
        }.`when`(repo).getReviewById(eq("nonexistent"), any())

        viewModel.getReviewById("nonexistent")

        Thread.sleep(100)

        assertNull(viewModel.review.value)
        verify(repo).getReviewById(eq("nonexistent"), any())
    }

    // Test 8: Get all reviews success
    @Test
    fun getAllReviews_success_test() {
        val repo = mock<ReviewRepo>()
        val viewModel = ReviewViewModel(repo)

        val mockReviews = listOf(
            ReviewModel("r1", "Alice", 5, "Amazing experience!"),
            ReviewModel("r2", "Bob", 4, "Really enjoyable trek."),
            ReviewModel("r3", "Charlie", 3, "It was decent.")
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, List<ReviewModel>?) -> Unit>(0)
            callback(true, "Reviews fetched successfully", mockReviews)
            null
        }.`when`(repo).getAllReviews(any())

        viewModel.getAllReviews()

        Thread.sleep(100)

        assertNotNull(viewModel.allReviews.value)
        assertEquals(3, viewModel.allReviews.value?.size)
        assertEquals("Alice", viewModel.allReviews.value?.get(0)?.reviewerName)
        assertEquals(4, viewModel.allReviews.value?.get(1)?.stars)
        assertEquals("It was decent.", viewModel.allReviews.value?.get(2)?.reviewText)
        verify(repo).getAllReviews(any())
    }

    // Test 9: Get all reviews failure
    @Test
    fun getAllReviews_failure_test() {
        val repo = mock<ReviewRepo>()
        val viewModel = ReviewViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, List<ReviewModel>?) -> Unit>(0)
            callback(false, "Failed to fetch reviews", null)
            null
        }.`when`(repo).getAllReviews(any())

        viewModel.getAllReviews()

        Thread.sleep(100)

        assertEquals(emptyList<ReviewModel>(), viewModel.allReviews.value)
        verify(repo).getAllReviews(any())
    }
}