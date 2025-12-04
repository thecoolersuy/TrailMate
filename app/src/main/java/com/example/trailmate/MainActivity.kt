package com.example.trailmate

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trailmate.ui.theme.Black
import com.example.trailmate.ui.theme.Pink
import com.example.trailmate.ui.theme.TrailMateTheme
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
              .background(White)
              .padding(padding)
              .fillMaxSize(),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
      ){
         Text("TrailMate",
             modifier = Modifier
                 .fillMaxWidth(),
             style = TextStyle(
                 fontSize = 36.sp,
                 fontWeight = FontWeight.SemiBold,
                 textAlign = TextAlign.Center
             ))
          Spacer(modifier = Modifier.padding(vertical =8.dp))
          Text("Find trusted guides anywhere.",
              modifier = Modifier.fillMaxWidth(),
              style = TextStyle(
                  fontSize = 15.sp,
                  textAlign = TextAlign.Center
              )
          )
          Spacer(modifier = Modifier.padding(vertical= 10.dp))
          Button(
              onClick = {
                  val intent = Intent(context, SignupActivity::class.java)

                  context.startActivity(intent)
              },
              modifier = Modifier
                  .width(210.dp)
                  .height(45.dp),
              shape = RoundedCornerShape(14.dp),
              colors = ButtonDefaults.buttonColors(
                  containerColor = Pink
              )
              ) {
              Text("Get Started",
                  style = TextStyle(
                      fontSize = 14.sp,
                      fontWeight = FontWeight.SemiBold
                  ))
          }
      }

  }
}

@Preview
@Composable
fun PreviewMainBody(){
    MainBody()
}
