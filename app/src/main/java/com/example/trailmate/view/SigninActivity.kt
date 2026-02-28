package com.example.trailmate.view

import android.content.Context
import android.content.Intent
import com.example.trailmate.R
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trailmate.repository.UserRepoImpl
import com.example.trailmate.ui.theme.BackButtonGrey
import com.example.trailmate.ui.theme.BackgroundWhite
import com.example.trailmate.ui.theme.Black
import com.example.trailmate.ui.theme.ButtonGreen
import com.example.trailmate.ui.theme.DarkGreen
import com.example.trailmate.ui.theme.LightGreen
import com.example.trailmate.ui.theme.StrokeGrey
import com.example.trailmate.ui.theme.TrailMateTheme
import com.example.trailmate.ui.theme.White
import com.example.trailmate.view.ForgetPasswordActivity
import com.example.trailmate.viewmodel.UserViewModel
import androidx.compose.ui.platform.testTag
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException


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
    var context = LocalContext.current
    val userViewModel = remember { UserViewModel(UserRepoImpl()) }
    val sharedPreferences = context.getSharedPreferences(
        "User",
        Context.MODE_PRIVATE
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                userViewModel.signInWithGoogle(idToken) { success, msg ->
                    if (success) {
                        val intent = Intent(context, DashboardActivity::class.java)
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: ApiException) {
            Toast.makeText(context, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    val googleSignInClient = remember {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }
    val localEmail: String? = sharedPreferences.getString("email", "")
    val localPassword: String? = sharedPreferences.getString("password", "")
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
                onClick = {
                    val intent = Intent(context, SignupActivity::class.java)

                    context.startActivity(intent)
                },
                modifier = Modifier
                    .testTag("backButton")
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
//                    fontWeight = FontWeight.ExtraBold,
                    color = DarkGreen,
                    textAlign = TextAlign.Left,
                    fontFamily = FontFamily(Font(R.font.outfitbold))
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
                    .padding(horizontal = 25.dp)
                    .testTag("emailInput"),
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
                    .padding(horizontal = 25.dp)
                    .testTag("passwordInput"),
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
                    .padding(horizontal = 25.dp)
                    .clickable {
                        val intent = Intent(context, ForgetPasswordActivity::class.java)
                        context.startActivity(intent)
                    }
                    .testTag("forgetPasswordLink"),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Right,
                    color =LightGreen ,
                    fontFamily = FontFamily(Font(R.font.inter))
                )
            )

            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Button(
                onClick = {
                    userViewModel.login(email,password){
                        success,message ->
                        if(success){
                            val intent= Intent(context, DashboardActivity::class.java)
                            context.startActivity(intent)
                        }else{
                            Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(horizontal = 25.dp)
                    .testTag("signInButton"),
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
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                )
                Text("or continue with",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                    ,
                    style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    color = LightGreen
                    )
                )
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                )

            }
            Spacer(modifier = Modifier.height(30.dp))
            Row (
                modifier = Modifier
                    .padding(horizontal = 25.dp)
            ){
                Button(onClick = {
                    launcher.launch(googleSignInClient.signInIntent)
                },
                    modifier = Modifier
                        .weight(1f)
                        .width(150.dp)
                        .height(50.dp)
                        .testTag("googleButton"),
                    colors = ButtonDefaults.buttonColors(
                          containerColor = BackgroundWhite,

                    ),
                    border = BorderStroke(2.dp, StrokeGrey),
                    shape =  RoundedCornerShape(12.dp)
                )


                {
                    Icon(
                        painter = painterResource(R.drawable.google),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                        tint = Color.Unspecified

                    )
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text("Google",
                        style = TextStyle(
                            color = Black
                        ))
                }
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Button(onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .width(150.dp)
                        .height(50.dp)
                        .testTag("facebookButton")
                    ,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BackgroundWhite,

                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(2.dp, StrokeGrey),

                ) {
                    Icon(
                        painter = painterResource(R.drawable.facebook),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text("Facebook",
                        style = TextStyle(
                            color = Black
                        ))

            }
            }
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    ,
                horizontalArrangement = Arrangement.Center
            ){
                Text("Don't have an account?",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        color = LightGreen,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontSize = 15.sp
                    )
                )
                Spacer(modifier = Modifier.padding(horizontal = 1.dp))
                Text("Sign Up",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        color = ButtonGreen,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontSize = 15.sp
                    ),
                modifier = Modifier
                    .testTag("signUpLink")
                    .clickable(){
                        val intent = Intent(context, SignupActivity:: class.java)
                        context.startActivity(intent)
                    })
            }
        }
    }
}

@Preview
@Composable
fun PreviewSigninBody(){
    SigninBody()
}
