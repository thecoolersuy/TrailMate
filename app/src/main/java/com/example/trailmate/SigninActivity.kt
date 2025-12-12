package com.example.trailmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trailmate.ui.theme.BackButtonGrey
import com.example.trailmate.ui.theme.BackgroundWhite
import com.example.trailmate.ui.theme.ButtonGreen
import com.example.trailmate.ui.theme.DarkGreen
import com.example.trailmate.ui.theme.LightGreen
import com.example.trailmate.ui.theme.StrokeGrey
import com.example.trailmate.ui.theme.TrailMateTheme
import com.example.trailmate.ui.theme.White

class SigninActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           SigninBody()
        }
    }
}

@Composable
fun SigninBody(){
    var fullname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmpassword by remember{ mutableStateOf("")}
    Scaffold(
        modifier = Modifier
            .fillMaxSize()

    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundWhite),


            ) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(20.dp)
                    .size(45.dp)
                    .background(
                        color = BackButtonGrey,
                        shape = RoundedCornerShape(12.dp)  // Adjust shape as needed
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_back_24),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = DarkGreen
                )
            }
            Spacer(modifier = Modifier.padding(top = 2.dp))
            Text(
                "Welcome Back",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DarkGreen,
                    textAlign = TextAlign.Left,
                    fontFamily = FontFamily(Font(R.font.outfit))
                )
            )

            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Text(
                "Sign in to continue your adventures",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    color = LightGreen,
                    textAlign = TextAlign.Left,
                    fontFamily = FontFamily(Font(R.font.outfit))
                )
            )
            Spacer(modifier = Modifier.padding(vertical = 25.dp))
            Text(
                "Email",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkGreen,
                    fontFamily = FontFamily(Font(R.font.outfit))
                )
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { data ->
                    email = data
                },
                placeholder = {
                    Text(
                        "Enter your email",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = LightGreen,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily(Font(R.font.inter))

                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(horizontal = 25.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = White,
                    focusedBorderColor = StrokeGrey,
                    unfocusedContainerColor = White,
                    unfocusedBorderColor = StrokeGrey
                ),
                shape = RoundedCornerShape(10.dp)
            )
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            Text(
                "Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkGreen,
                    fontFamily = FontFamily(Font(R.font.outfit))
                )
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { data ->
                    password = data
                },
                placeholder = {
                    Text(
                        "Create a password",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = LightGreen,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily(Font(R.font.inter))
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(horizontal = 25.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = White,
                    focusedBorderColor = StrokeGrey,
                    unfocusedContainerColor = White,
                    unfocusedBorderColor = StrokeGrey
                ),
                shape = RoundedCornerShape(10.dp)
            )
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            Text(
                "Forgot Password?",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Right,
                    color = DarkGreen,
                    fontFamily = FontFamily(Font(R.font.outfit))
                )
            )

            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(horizontal = 25.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonGreen
                )
            ) {
                Text(
                    "Sign In",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(R.font.outfit))
                    )
                )


            }
        }
    }
}

@Preview
@Composable
fun PreviewSigninBody(){
    SigninBody()
}
