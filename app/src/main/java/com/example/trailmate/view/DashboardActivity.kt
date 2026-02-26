package com.example.trailmate.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.trailmate.R
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.trailmate.ui.theme.TrailMateTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trailmate.repository.PackageRepoImpl
import com.example.trailmate.ui.theme.BackgroundWhite
import com.example.trailmate.ui.theme.ButtonGreen
import com.example.trailmate.ui.theme.White
import com.example.trailmate.viewmodel.PackageViewModel

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           DashboardBody()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardBody(){
    val context = LocalContext.current
    val activity = context as Activity
    val email = activity.intent.getStringExtra("email")
    val password = activity.intent.getStringExtra("password")
    data class NavItem(val label:String, val icon : Int)
    var selectedIndex by remember { mutableStateOf(0) }

    val navList = listOf(
        NavItem(label = "Home", icon = R.drawable.home),
        NavItem(label = "Explore", icon = R.drawable.compass),
        NavItem(label = "Reviews", icon = R.drawable.calendar),
        NavItem(label = "Profile", icon = R.drawable.user),
    )

    Scaffold(
        floatingActionButton ={
            FloatingActionButton(onClick = {
                val intent = Intent(
                    context,
                    CreatePackage::class.java
                )
                context.startActivity(intent)
            }){
                Icon(
                    Icons.Default.Add, contentDescription = null
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = BackgroundWhite,
                tonalElevation = 0.dp
            ) {
                navList.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = null,
                                tint = ButtonGreen,
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        },
                        label = {
                            Text(item.label,
                                style = TextStyle(
                                    color = ButtonGreen,
                                    fontFamily = FontFamily(Font(R.font.outfit)),
                                    fontWeight = FontWeight.SemiBold
                                ))
                        },
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        modifier = Modifier.background(BackgroundWhite)
                            .padding(top = 15.dp)

                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when(selectedIndex){
                0 -> HomeScreen()
                1 -> ExploreScreen()
                2 -> ReviewScreen()
                3 -> ProfileScreen()
                else -> HomeScreen()
            }
        }
    }
}

@Preview
@Composable
fun PreviewDashboardBody(){
    DashboardBody()
}

