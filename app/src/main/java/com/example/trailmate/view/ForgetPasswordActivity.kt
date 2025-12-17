package com.example.trailmate.view

import android.app.Activity
import com.example.trailmate.R
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.trailmate.repository.UserRepoImpl
import com.example.trailmate.viewmodel.UserViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trailmate.repository.UserRepo
import com.example.trailmate.ui.theme.BackButtonGrey
import com.example.trailmate.ui.theme.BackgroundWhite
import com.example.trailmate.ui.theme.ButtonGreen
import com.example.trailmate.ui.theme.DarkGreen

import com.example.trailmate.ui.theme.DeepGreen
import com.example.trailmate.ui.theme.LightGreen
import com.example.trailmate.ui.theme.StrokeGrey
import com.example.trailmate.ui.theme.White
import com.example.trailmate.view.ui.theme.PurpleGrey80


class ForgetPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ForgetPasswordBody()
        }
    }
}

@Composable
fun ForgetPasswordBody(

) {
    var email by remember { mutableStateOf("") }

    var userViewModel = remember { UserViewModel(UserRepoImpl()) }

    val context = LocalContext.current
    val activity = context as? Activity

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundWhite),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    val intent = Intent(context, SignupActivity::class.java)

                    context.startActivity(intent)
                },
                modifier = Modifier
                    .padding(20.dp)
                    .size(45.dp)
                    .background(
                        color = BackButtonGrey,
                        shape = RoundedCornerShape(12.dp)
                        // Adjust shape as needed
                    )
                    .align (Alignment.Start ),

            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_back_24),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = DarkGreen
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        DeepGreen,
                        shape = CircleShape
                    ),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    painter = painterResource(R.drawable.locker),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp),
                    tint = White
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text("Forgot Password?",
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
            Spacer(modifier = Modifier.height(15.dp))
            Text("No worries! Enter your email address and we'll send you a link to reset your password.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = LightGreen,
                    textAlign = TextAlign.Left,
                    fontFamily = FontFamily(Font(R.font.inter))
                ))
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                "Email Address",
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
                        "Enter your registered email",
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


            Spacer(modifier = Modifier.height(15.dp))

            Button (
                    onClick = {
                        userViewModel.forgetPassword(email){
                                success,message->
                            if (success){
                                Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
                                activity?.finish()
                            }else{
                                Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
                            }
                        }

                    },
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 10.dp

                    ),

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(horizontal = 25.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonGreen
                )
            ) {
                Text("Send Reset Link",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(R.font.outfit))
                    ))


            }
        }
    }
}

@Preview
@Composable
fun PreviewForgetPasswordBody() {
    ForgetPasswordBody()
}
