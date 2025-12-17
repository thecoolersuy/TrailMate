package com.example.trailmate.view

import android.content.Intent
import com.example.trailmate.R
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.trailmate.model.UserModel
import com.example.trailmate.repository.UserRepoImpl
import com.example.trailmate.ui.theme.BackButtonGrey
import com.example.trailmate.ui.theme.BackgroundWhite
import com.example.trailmate.ui.theme.Black
import com.example.trailmate.ui.theme.Blue
import com.example.trailmate.ui.theme.ButtonGreen
import com.example.trailmate.ui.theme.DarkGreen
import com.example.trailmate.ui.theme.Grey
import com.example.trailmate.ui.theme.LightBlue
import com.example.trailmate.ui.theme.LightGreen
import com.example.trailmate.ui.theme.PurpleGrey40
import com.example.trailmate.ui.theme.StrokeGrey

import com.example.trailmate.ui.theme.TrailMateTheme
import com.example.trailmate.ui.theme.White
import com.example.trailmate.viewmodel.UserViewModel
import java.nio.file.WatchEvent

class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
             SignupBody()
        }
    }
}

@Composable
fun SignupBody(){
    val userViewModel = remember { UserViewModel(UserRepoImpl()) }

    var fullname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmpassword by remember{ mutableStateOf("")}
    var terms by remember { mutableStateOf(false) }
    var context = LocalContext.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize()

    ) {padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundWhite),


        ){
            IconButton(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)

                    context.startActivity(intent)
                },
                modifier = Modifier
                    .padding( 20.dp)
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
            Text("Create Account",
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
            Text("Start your adventure journey today",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    color = LightGreen,
                    textAlign = TextAlign.Left,
                    fontFamily = FontFamily(Font(R.font.outfit))
                ))
            Spacer(modifier = Modifier.padding(vertical = 25.dp))
            Text("Full Name",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    color = DarkGreen,
                    fontFamily = FontFamily(Font(R.font.outfit))
                )
                )
            Spacer(modifier = Modifier.padding(4.dp))
            OutlinedTextField(
                value = fullname,
                onValueChange = { data ->
                    fullname = data
                },
                placeholder = {
                    Text(
                        "Enter your full name",
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
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
            Text("Email",
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
            Text("Password",
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
            Text("Confirm password",
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
                    value = confirmpassword,
                    onValueChange = { data ->
                        confirmpassword = data
                    },
                    placeholder = {
                        Text(
                            "Confirm your password",
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
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Button(
                    onClick = {

                            userViewModel.register( email, password){
                                success, msg,userId ->
                                if(success) {
                                    val model = UserModel(
                                        userId = userId,
                                        fullName = fullname,
                                        email = email,
                                        password = password
                                    )
                                    userViewModel.addUserToDatabase(userId, model) { success, msg ->
                                        if (success) {
                                            Toast.makeText(
                                                context,
                                                msg,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                context,
                                                msg,
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        }
                                    }
                                }else{
                                    Toast.makeText(context,
                                        msg,
                                        Toast.LENGTH_SHORT).show()
                                }
                            }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .padding(horizontal = 25.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonGreen
                    )
                ) {
                    Text("Create Account",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily(Font(R.font.outfit))
                        ))


                }
            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically

            ) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .weight(1f)

                )
                Text(
                    "or sign up with",

                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        color = LightGreen
                    )
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .weight(1f)

                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row() {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(horizontal = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,

                    ),
                    border = BorderStroke(2.dp ,StrokeGrey),
                    shape = RoundedCornerShape(12.dp)

                    ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.google),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color.Unspecified

                        )
                        Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                        Text("Google",
                            style = TextStyle(
                                color = Black,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight.Normal
                            ))
                    }

                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(horizontal = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,

                        ),
                    border = BorderStroke(2.dp ,StrokeGrey),
                    shape = RoundedCornerShape(12.dp)
                    ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.facebook),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color.Unspecified

                        )
                        Spacer(modifier = Modifier.padding(horizontal = 6.dp))



                        Text("Facebook",
                            style = TextStyle(
                                color = Black,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight.Normal
                            ))

                    }


                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row (
                 modifier = Modifier
                     .padding(horizontal = 75.dp)
            ){
                Text(
                    text = "Already have an account? ",
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        ,
                    color = LightGreen,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.inter))
                )
                Text(
                    text = "Sign In",
                    color = Color(0xFF008080),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    modifier = Modifier
                        .clickable(){
                            val intent = Intent(context, SigninActivity::class.java)

                            context.startActivity(intent)
                    }
                )
            }

        }


    }
}

@Preview
@Composable
fun PreviewSignup(){
    SignupBody()
}


