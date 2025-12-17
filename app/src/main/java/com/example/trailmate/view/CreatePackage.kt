package com.example.trailmate.view

import android.R
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.tooling.preview.Preview
import com.example.trailmate.ui.theme.TrailMateTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trailmate.model.PackageModel
import com.example.trailmate.model.UserModel
import com.example.trailmate.ui.theme.ButtonGreen
import com.example.trailmate.ui.theme.DarkGreen


class CreatePackage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CreatePackageBody()
        }
    }
}


@Composable
fun CreatePackageBody(){
    Scaffold (

    ){ padding->
        var packageName by remember { mutableStateOf("") }
        var packageDuration by remember {mutableStateOf("")}
        var packageCapacity by remember { mutableStateOf("") }
        var packageDifficulty by remember { mutableStateOf("") }
        var packagePrice by remember { mutableStateOf("") }


        Column(
            modifier = Modifier.padding(padding)
                .padding(50.dp),

        ) {
            Text("Create Package",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DarkGreen,
                    textAlign = TextAlign.Left,
                    fontFamily = FontFamily(Font(com.example.trailmate.R.font.outfit))
                ))
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = packageName,
                onValueChange = {
                    data -> packageName = data
                },
                placeholder = {
                    Text("e.g. Mt. Manaslu Circuit Trek")
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = packageDuration,
                onValueChange = {
                        data -> packageDuration = data
                },
                placeholder = {
                    Text("SELECT")
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = packageDifficulty,
                onValueChange = {
                        data -> packageDifficulty = data
                },
                placeholder = {
                    Text("SELECT")
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = packagePrice,
                onValueChange = {
                        data -> packagePrice = data
                },
                placeholder = {
                    Text("e.g. 2500")
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = packageCapacity,
                onValueChange = {
                        data -> packageCapacity = data
                },
                placeholder = {
                    Text("Max 15")
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    
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
                Text(
                    "Add package",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(com.example.trailmate.R.font.outfit))
                    )
                )
            }

        }
    }

}

@Preview
@Composable
fun PreviewCreatePackageBody(){
    CreatePackageBody()
}

