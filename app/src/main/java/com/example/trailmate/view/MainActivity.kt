package com.example.trailmate.view;

import com.example.trailmate.R
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.example.trailmate.ui.theme.Green
import com.example.trailmate.ui.theme.StrokeGrey
import com.example.trailmate.ui.theme.TextGreen
import com.example.trailmate.ui.theme.White


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainBody()
        }
    }
}

@Composable
fun MainBody(){
    var context = LocalContext.current
  Scaffold (
      modifier = Modifier.fillMaxSize()
  ){ padding ->
      Column (
          modifier = Modifier
              .background(Green)
              .padding(padding)
              .fillMaxSize()
              .verticalScroll(rememberScrollState()),

          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
      ){
          Spacer(modifier = Modifier.height(50.dp))
          Image(
              painter = painterResource(R.drawable.vectorlogin),
              contentDescription = null,
              modifier = Modifier
                  .width(400.dp)
                  .height(300.dp)
          )
          Spacer(modifier = Modifier.height(40.dp))
         Text("TrailMate",
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(horizontal = 40.dp),
             style = TextStyle(
                 fontSize = 42.sp,
//                 fontWeight = FontWeight.ExtraBold,
                 textAlign = TextAlign.Left,
                 color = White,
                 fontFamily = FontFamily(Font(R.font.outfitbold))
             ))
          Spacer(modifier = Modifier.padding(vertical =8.dp))
          Text("Discover extraordinary adventures\n" +
                  "with expert local guides. Your journey\n" +
                  "starts here.",
              modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 36.dp),
              style = TextStyle(
                  fontSize = 18.sp,
                  textAlign = TextAlign.Left,
                  color = White,
                  fontWeight = FontWeight.Normal,
                  fontFamily = FontFamily(Font(R.font.inter))
              )
          )
          Spacer(modifier = Modifier.height(15.dp))
          Button(
              onClick = {
                  val intent = Intent(context, SignupActivity::class.java)

                  context.startActivity(intent)
              },
              modifier = Modifier
                  .width(320.dp)
                  .height(55.dp),
              shape = RoundedCornerShape(14.dp),
              colors = ButtonDefaults.buttonColors(
                  containerColor = White
              )
              ) {
              Text("Get Started",
                  style = TextStyle(
                      fontSize = 15.sp,
                      fontWeight = FontWeight.Bold,
                      color = TextGreen,
                      fontFamily = FontFamily(Font(R.font.inter))
                  ))
          }
          Spacer(modifier = Modifier.padding(vertical = 8.dp))
          Button(
              onClick = {
                  val intent = Intent(context, SigninActivity::class.java)

                  context.startActivity(intent)
              },
              modifier = Modifier
                  .width(320.dp)
                  .height(55.dp),
              shape = RoundedCornerShape(14.dp),
              colors = ButtonDefaults.buttonColors(
                  containerColor = Green

              ),
              border = BorderStroke(1.5.dp, White.copy(alpha = 0.2f))
          ) {
              Text("I already have an account",
                  style = TextStyle(
                      fontSize = 15.sp,
                      fontWeight = FontWeight.Bold,
                      color = White,
                      fontFamily = FontFamily(Font(R.font.inter))
                  ))

          }
          Spacer(modifier = Modifier.height(50.dp))
          Text("By continuing, you agree to our Terms & Privacy Policy",

              style = TextStyle(
                  fontSize = 13.sp,
                  color = StrokeGrey.copy(alpha = 0.8f),
                  fontFamily = FontFamily(Font(R.font.inter))
              ),
              modifier = Modifier
                  .padding(bottom = 25.dp)
                  .padding(horizontal = 32.dp)
              )
      }

  }
}

@Preview
@Composable
fun PreviewMainBody(){
    MainBody()
}
