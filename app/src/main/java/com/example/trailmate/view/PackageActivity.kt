package com.example.trailmate.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.trailmate.R
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trailmate.ui.theme.TrailMateTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.trailmate.ui.theme.BackgroundWhite
import com.example.trailmate.ui.theme.White

class PackageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           PackageActivityBody()
        }
    }
}

@Composable
fun PackageActivityBody(){
      Scaffold (

      ){ padding ->
          var searchPackage by remember { mutableStateOf("") }
          Column(
              modifier = Modifier.padding(padding)
                  .background(BackgroundWhite)
          ) {
              Text("Explore Packages")
              Spacer(modifier = Modifier.height(20.dp))
              OutlinedTextField(
                  value = searchPackage,
                  modifier = Modifier.fillMaxWidth()
                      .padding(horizontal = 20.dp)
                      .background(White),
                  shape = RoundedCornerShape(16.dp),
                  onValueChange = { data ->
                      searchPackage = data
                  },
                  placeholder = {
                      Row() {
                          Icon(
                              painter = painterResource(
                                  R.drawable.google
                              ),
                              contentDescription = null
                          )
                          Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                          Text("Search Packages...")
                      }

                  }
              )
              Spacer(modifier = Modifier.height(30.dp))
              Card {
                  Column {
                      Image(
                          painter = painterResource(R.drawable.vectorlogin),
                          contentDescription = null
                          )
                      Text("Mount Manaslu Circuit Trek")
                      Text("3 days")
                      Text("3 days")
                  }
              }



          }
      }
}

@Preview
@Composable
fun PreviewPackageActivityBody(){
    PackageActivityBody()
}

