package com.example.trailmate.view

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trailmate.R
import com.example.trailmate.model.PackageModel
import com.example.trailmate.model.UserModel
import com.example.trailmate.repository.UserRepoImpl
import com.example.trailmate.ui.theme.BackgroundWhite
import com.example.trailmate.ui.theme.DarkGreen
import com.example.trailmate.ui.theme.Green
import com.example.trailmate.ui.theme.LightGreen
import com.example.trailmate.ui.theme.OrangeText
import com.example.trailmate.ui.theme.White
import com.example.trailmate.viewmodel.UserViewModel
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExitToApp

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val activity = context as? Activity
    val userViewModel = remember { UserViewModel(UserRepoImpl()) }
    val currentUser = userViewModel.getCurrentUser()
    val user = userViewModel.users.observeAsState(initial = null)
    var showDialog by remember { mutableStateOf(false) }
    var fullName by remember { mutableStateOf("") }
    var location by remember {mutableStateOf("")}

    LaunchedEffect(currentUser) {
        currentUser?.uid?.let { userId ->
            userViewModel.getUserById(userId)
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundWhite)
                .padding(padding)
        ) {
            // Header with gradient background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Green,
                                Green.copy(alpha = 0.9f)
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        "PROFILE",
                        style = TextStyle(
                            color = White,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.outfitbold)),
                            letterSpacing = 2.sp
                        ),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    // User Name
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                showDialog = false
                            },
                            confirmButton = {
                                TextButton(onClick = {
                                    val model = UserModel(
                                        user.value!!.userId,
                                        user.value!!.fullName,
                                        user.value!!.location
                                    )

                                }) { Text("Update") }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    showDialog = false
                                })
                                {
                                    Text("Cancel")
                                }
                            },
                            title = { Text("Update Package") },
                            text = {
                                Column {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    OutlinedTextField(
                                        value = fullName,
                                        onValueChange = { data ->
                                            fullName = data
                                        },
                                        placeholder = {
                                            Text("e.g. Mt. Manaslu Circuit Trek")
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    OutlinedTextField(
                                        value = location,
                                        onValueChange = { data ->
                                            location = data
                                        },
                                        placeholder = {
                                            Text("Duration")
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            }
                        )
                    }
                    Text(
                        text = user.value?.fullName ?: "User Name",
                        style = TextStyle(
                            color = White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.outfit))
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Email
                    Text(
                        text = user.value?.email ?: "user@email.com",
                        style = TextStyle(
                            color = White.copy(alpha = 0.9f),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.inter))
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Stats Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatItem(number = "0", label = "Trips")
                        StatItem(number = "0", label = "Guides")
                        StatItem(number = "0", label = "Reviews")
                    }
                }
            }

            // Options Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 24.dp)
            ) {
                // Become a Guide Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* Handle click */ },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = OrangeText
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = White,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Become a Guide",
                                style = TextStyle(
                                    color = White,
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.outfitbold))
                                )
                            )
                            Text(
                                "Share your expertise and earn",
                                style = TextStyle(
                                    color = White.copy(alpha = 0.9f),
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.inter))
                                )
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null,
                            tint = White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                ProfileMenuItem(
                    icon = Icons.Default.ExitToApp,
                    title = "Logout",
                    onClick = {
                        userViewModel.logOut { success, msg ->
                            if (success) {
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                context.startActivity(intent)
                            }
                        }
                    }
                )




                Spacer(modifier = Modifier.height(12.dp))

                // Payment Methods Option
                ProfileMenuItem(
                    icon = Icons.Default.Check,
                    title = "Payment Methods",
                    onClick = {
                        // Navigate to payment methods
                    }
                )
            }
        }
    }
}

@Composable
fun StatItem(number: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = number,
            style = TextStyle(
                color = White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.outfit))
            )
        )
        Text(
            text = label,
            style = TextStyle(
                color = White.copy(alpha = 0.8f),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.inter))
            )
        )
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(BackgroundWhite),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = DarkGreen,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = TextStyle(
                    color = DarkGreen,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.outfit))
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                tint = LightGreen
            )
        }
    }
}