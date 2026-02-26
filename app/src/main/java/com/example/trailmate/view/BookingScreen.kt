package com.example.trailmate.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trailmate.R
import com.example.trailmate.model.ReviewModel
import com.example.trailmate.repository.ReviewRepoImpl
import com.example.trailmate.ui.theme.BackgroundWhite
import com.example.trailmate.ui.theme.ButtonGreen
import com.example.trailmate.ui.theme.DarkGreen
import com.example.trailmate.ui.theme.Green
import com.example.trailmate.ui.theme.White
import com.example.trailmate.viewmodel.ReviewViewModel

@Composable
fun BookingScreen() {
    val reviewViewModel = remember { ReviewViewModel(ReviewRepoImpl()) }
    val context = LocalContext.current

    val review = reviewViewModel.review.observeAsState(initial = null)
    val allReviews = reviewViewModel.allReviews.observeAsState(initial = emptyList())
    val loading = reviewViewModel.loading.observeAsState(initial = false)

    // Add dialog state
    var showAddDialog by remember { mutableStateOf(false) }

    // Edit dialog state
    var showEditDialog by remember { mutableStateOf(false) }

    // Fields for Add
    var reviewerName by remember { mutableStateOf("") }
    var stars by remember { mutableStateOf("") }
    var reviewText by remember { mutableStateOf("") }

    // Fields for Edit
    var editReviewerName by remember { mutableStateOf("") }
    var editStars by remember { mutableStateOf("") }
    var editReviewText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        reviewViewModel.getAllReviews()
    }

    // Populate edit fields when review loads
    LaunchedEffect(review.value) {
        review.value?.let {
            editReviewerName = it.reviewerName
            editStars = it.stars.toString()
            editReviewText = it.reviewText
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = ButtonGreen
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Review", tint = White)
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundWhite)
                .padding(padding)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Green)
                    .padding(vertical = 20.dp, horizontal = 24.dp)
            ) {
                Text(
                    "Reviews",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = White,
                        fontFamily = FontFamily(Font(R.font.outfitbold))
                    )
                )
            }

            // ── ADD DIALOG ──
            if (showAddDialog) {
                AlertDialog(
                    onDismissRequest = { showAddDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            val starsInt = stars.toIntOrNull() ?: 0
                            val model = ReviewModel(
                                reviewerName = reviewerName,
                                stars = starsInt,
                                reviewText = reviewText
                            )
                            reviewViewModel.addReview(model) { success, msg ->
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                if (success) {
                                    showAddDialog = false
                                    reviewerName = ""
                                    stars = ""
                                    reviewText = ""
                                }
                            }
                        }) { Text("Add") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showAddDialog = false }) {
                            Text("Cancel")
                        }
                    },
                    title = { Text("Write a Review") },
                    text = {
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = reviewerName,
                                onValueChange = { reviewerName = it },
                                placeholder = { Text("Your name") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedTextField(
                                value = stars,
                                onValueChange = { stars = it },
                                placeholder = { Text("Stars (1–5)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedTextField(
                                value = reviewText,
                                onValueChange = { reviewText = it },
                                placeholder = { Text("Write your review...") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 3
                            )
                        }
                    }
                )
            }

            // ── EDIT DIALOG ──
            if (showEditDialog) {
                AlertDialog(
                    onDismissRequest = { showEditDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            val model = ReviewModel(
                                reviewId = review.value!!.reviewId,
                                reviewerName = editReviewerName,
                                stars = editStars.toIntOrNull() ?: 0,
                                reviewText = editReviewText
                            )
                            reviewViewModel.editReview(model) { success, msg ->
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                if (success) showEditDialog = false
                            }
                        }) { Text("Update") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showEditDialog = false }) {
                            Text("Cancel")
                        }
                    },
                    title = { Text("Update Review") },
                    text = {
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = editReviewerName,
                                onValueChange = { editReviewerName = it },
                                placeholder = { Text("Your name") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedTextField(
                                value = editStars,
                                onValueChange = { editStars = it },
                                placeholder = { Text("Stars (1–5)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedTextField(
                                value = editReviewText,
                                onValueChange = { editReviewText = it },
                                placeholder = { Text("Write your review...") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 3
                            )
                        }
                    }
                )
            }

            // ── REVIEW LIST ──
            if (loading.value) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(allReviews.value ?: emptyList()) { data ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = White)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {

                                // Reviewer name + edit/delete buttons
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        data.reviewerName,
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = DarkGreen,
                                            fontFamily = FontFamily(Font(R.font.outfitbold))
                                        )
                                    )
                                    Row {
                                        IconButton(onClick = {
                                            showEditDialog = true
                                            reviewViewModel.getReviewById(data.reviewId)
                                        }) {
                                            Icon(
                                                Icons.Default.Edit,
                                                contentDescription = null,
                                                tint = Color.Green
                                            )
                                        }
                                        IconButton(onClick = {
                                            reviewViewModel.deleteReview(data.reviewId) { success, msg ->
                                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                            }
                                        }) {
                                            Icon(
                                                Icons.Default.Delete,
                                                contentDescription = null,
                                                tint = Color.Red
                                            )
                                        }
                                    }
                                }

                                // Stars row
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    repeat(5) { index ->
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = if (index < data.stars) Color(0xFFFFC107) else Color.LightGray,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        "${data.stars}/5",
                                        style = TextStyle(
                                            fontSize = 13.sp,
                                            color = Color.Gray
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Review text
                                Text(
                                    data.reviewText,
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = Color.DarkGray,
                                        fontFamily = FontFamily(Font(R.font.outfitregular))
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewBookingScreen() {
    BookingScreen()
}