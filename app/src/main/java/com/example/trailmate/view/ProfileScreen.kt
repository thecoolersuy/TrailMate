package com.example.trailmate.view

import androidx.compose.foundation.Image
import com.example.trailmate.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trailmate.ui.theme.BackgroundWhite
import com.example.trailmate.ui.theme.Blue
import com.example.trailmate.ui.theme.DarkGreen
import com.example.trailmate.ui.theme.LightGreen
import com.example.trailmate.ui.theme.White

@Composable
fun ProfileScreen(){
    Scaffold (){ padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundWhite)

        ){
            Text("Profile",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                style = TextStyle(
                    color = DarkGreen,
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(R.font.outfitbold))
                )
            )
            Card (
                modifier = Modifier
                    .padding(22.dp)
                    .shadow(
                        elevation = 4.dp
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = White
                )

            ){
                Column {
                    Text("name",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        style = TextStyle(
                            color = DarkGreen,
                            fontSize = 23.sp,
                            fontFamily = FontFamily(Font(R.font.outfitbold))
                        )
                    )
                    Text("location",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        style = TextStyle(
                            color = LightGreen,
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.outfitregular))
                        )
                    )
                    Row {
                        Column {  }
                        Column {  }
                        Column {  }
                    }
                }
            }
        }

    }
}