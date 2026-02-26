package com.example.trailmate.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.trailmate.ui.theme.LightGreen
import com.example.trailmate.ui.theme.StrokeGrey
import com.example.trailmate.ui.theme.White
import com.example.trailmate.viewmodel.ReviewViewModel

@Composable
fun ReviewScreen() {
    val reviewViewModel = remember { ReviewViewModel(ReviewRepoImpl()) }
    val context = LocalContext.current

    val review = reviewViewModel.review.observeAsState(initial = null)
    val allReviews = reviewViewModel.allReviews.observeAsState(initial = emptyList())
    val loading = reviewViewModel.loading.observeAsState(initial = false)

    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    // Add fields
    var reviewerName by remember { mutableStateOf("") }
    var stars by remember { mutableStateOf("") }
    var reviewText by remember { mutableStateOf("") }

    // Edit fields
    var editReviewerName by remember { mutableStateOf("") }
    var editStars by remember { mutableStateOf("") }
    var editReviewText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        reviewViewModel.getAllReviews()
    }

    LaunchedEffect(review.value) {
        review.value?.let {
            editReviewerName = it.reviewerName
            editStars = it.stars.toString()
            editReviewText = it.reviewText
        }
    }

    // ── ADD DIALOG ──
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    val model = ReviewModel(
                        reviewerName = reviewerName,
                        stars = stars.toIntOrNull() ?: 0,
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
                }) { Text("Submit") }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) { Text("Cancel") }
            },
            title = { Text("Leave a Review") },
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
                TextButton(onClick = { showEditDialog = false }) { Text("Cancel") }
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

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundWhite)
                .padding(padding)
        ) {

            // ── HEADER ──
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Green)
                        .padding(horizontal = 24.dp, vertical = 24.dp)
                ) {
                    Column {
                        Text(
                            "Traveler Reviews",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = White,
                                fontFamily = FontFamily(Font(R.font.outfitbold))
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Share your trail experience",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = White.copy(alpha = 0.75f),
                                fontFamily = FontFamily(Font(R.font.outfitregular))
                            )
                        )
                    }
                }
            }

            // ── LEAVE A REVIEW BUTTON ──
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { showAddDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .padding(horizontal = 24.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonGreen
                    )
                ) {
                    Text(
                        "Leave a Review",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = White,
                            fontFamily = FontFamily(Font(R.font.outfit))
                        )
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // ── LOADING ──
            if (loading.value) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = ButtonGreen)
                    }
                }
            }

            // ── REVIEW LIST ──
            items(allReviews.value ?: emptyList()) { data ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(3.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        // Name + Edit/Delete
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

                        // Stars
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
                                    color = LightGreen
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Divider(color = StrokeGrey, thickness = 0.8.dp)

                        Spacer(modifier = Modifier.height(8.dp))

                        // Review paragraph
                        Text(
                            data.reviewText,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.DarkGray,
                                fontFamily = FontFamily(Font(R.font.outfitregular)),
                                lineHeight = 20.sp
                            )
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Preview
@Composable
fun PreviewBookingScreen() {
    ReviewScreen()
}