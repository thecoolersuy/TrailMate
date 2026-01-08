package com.example.trailmate.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.example.trailmate.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trailmate.repository.PackageRepoImpl
import com.example.trailmate.ui.theme.BackgroundWhite
import com.example.trailmate.ui.theme.Blue
import com.example.trailmate.ui.theme.Green
import com.example.trailmate.ui.theme.OffWhite
import com.example.trailmate.ui.theme.White
import com.example.trailmate.viewmodel.PackageViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.trailmate.repository.UserRepoImpl
import com.example.trailmate.ui.theme.BackButtonGrey
import com.example.trailmate.ui.theme.ButtonGreen
import com.example.trailmate.ui.theme.DarkGreen
import com.example.trailmate.ui.theme.Grey
import com.example.trailmate.ui.theme.LightGreen
import com.example.trailmate.ui.theme.OrangeText
import com.example.trailmate.ui.theme.StrokeGrey
import com.example.trailmate.viewmodel.UserViewModel


@Composable
fun HomeScreen(){
    val userViewModel = remember { UserViewModel(UserRepoImpl()) }
    val allUsers = userViewModel.allUsers.observeAsState(initial = emptyList())
    LaunchedEffect(Unit) {
        userViewModel.getAllUser()

    }

    Scaffold () { padding ->
        Box(modifier = Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(R.drawable.heromountain),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        )
                    )
                    .padding(20.dp)
            ) {
                Text(
                    "Welcome back",
                    style = TextStyle(
                        color = White,
                        fontFamily = FontFamily(Font(R.font.outfitregular)),
                        fontSize = 16.sp
                    )
                )
                Spacer(modifier = Modifier.padding(vertical = 1.dp))
                Text(
                    "Explorer",
                    style = TextStyle(
                        color = White,
                        fontFamily = FontFamily(Font(R.font.outfitbold)),
                        fontSize = 26.sp
                    )
                )
                Spacer(modifier = Modifier.padding(vertical = 84.dp))
                Text(
                    "Find Your Perfect",
                    style = TextStyle(
                        color = White,
                        fontFamily = FontFamily(Font(R.font.outfitbold)),
                        fontSize = 35.sp
                    )
                )
                Spacer(modifier = Modifier.padding(vertical = 1.dp))
                Text(
                    "Trail Guide",
                    style = TextStyle(
                        color = OrangeText,
                        fontFamily = FontFamily(Font(R.font.outfitbold)),
                        fontSize = 35.sp
                    )
                )
                Spacer(modifier= Modifier.padding(vertical = 3.dp))
                Text(
                    "250+ verified guides worldwide",
                    style = TextStyle(
                        color = White,
                        fontFamily = FontFamily(Font(R.font.outfitmedium)),
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
            }

            Column(
                modifier = Modifier
                    .padding(top = 410.dp)
                    .background(BackgroundWhite)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.padding(top = 65.dp))
                Row {
                    Text(
                        "Featured Trekkers",
                        style = TextStyle(
                            color = DarkGreen,
                            fontFamily = FontFamily(Font(R.font.outfitbold)),
                            fontSize = 25.sp
                        ),
                        modifier = Modifier.padding(horizontal = 25.dp)
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BackgroundWhite)
                ) {
                    items(allUsers.value!!.size) { index ->
                        val data = allUsers.value!![index]
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp)
                                .padding(horizontal = 22.dp)
                                .shadow(
                                    elevation = 4.dp,
                                    shape = RoundedCornerShape(12.dp),
                                    clip = false
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(White)
                                    .padding(30.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(White)
                                ) {
                                    Text(
                                        data.fullName,
                                        style = TextStyle(
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            fontFamily = FontFamily(Font(R.font.outfit)),
                                            color = DarkGreen
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(
                        "Search destinations...",
                        modifier = Modifier.padding(top = 7.dp),
                        style = TextStyle(
                            color = LightGreen,
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.outfitregular))
                        )
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.search),
                        contentDescription = null,
                        modifier = Modifier
                            .size(34.dp)
                            .padding(horizontal = 8.dp),
                        tint = LightGreen
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .offset(y = 380.dp)
                    .height(68.dp)
                    .shadow(
                        elevation = 3.dp,
                        shape = RoundedCornerShape(16.dp),
                        clip = false
                    ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    focusedBorderColor = White,
                    unfocusedBorderColor = White
                ),
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}



