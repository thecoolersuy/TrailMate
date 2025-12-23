package com.example.trailmate.view

import android.widget.Toast
import androidx.compose.foundation.Image
import com.example.trailmate.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trailmate.repository.PackageRepoImpl
import com.example.trailmate.ui.theme.BackButtonGrey
import com.example.trailmate.ui.theme.BackgroundWhite
import com.example.trailmate.ui.theme.Black
import com.example.trailmate.ui.theme.Blue
import com.example.trailmate.ui.theme.DarkGreen
import com.example.trailmate.ui.theme.Grey
import com.example.trailmate.ui.theme.LightGreen
import com.example.trailmate.ui.theme.StrokeGrey
import com.example.trailmate.ui.theme.White
import com.example.trailmate.viewmodel.PackageViewModel
import coil.compose.AsyncImage
import com.example.trailmate.model.PackageModel
import com.example.trailmate.ui.theme.ButtonGreen
import com.example.trailmate.ui.theme.Green

@Composable
fun ExploreScreen() {
    val packageViewModel = remember { PackageViewModel(PackageRepoImpl()) }
    val context = LocalContext.current

    val packages = packageViewModel.packages.observeAsState(initial = null)
    val allPackages = packageViewModel.allPackages.observeAsState(initial = emptyList())

    var showDialog by remember { mutableStateOf(false) }
    var packageName by remember { mutableStateOf("") }
    var packageDuration by remember { mutableStateOf("") }
    var packageCapacity by remember { mutableStateOf("") }
    var packageDifficulty by remember { mutableStateOf("") }
    var packagePrice by remember { mutableStateOf("") }

    val loading = packageViewModel.loading.observeAsState(initial = false)

    LaunchedEffect(packages.value) {
        packageViewModel.getAllPackage()
        packages.value?.let {
            packageName = it.packageName
            packageDuration = it.packageDuration.toString()
            packageCapacity = it.packageCapacity.toString()
            packageDifficulty = it.packageDifficulty
            packagePrice = it.packagePrice.toString()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Explore Packages",
            style = TextStyle(
                color = DarkGreen,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                fontFamily = FontFamily(Font(R.font.outfit))
            ),
            modifier = Modifier
                .padding(horizontal = 25.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text("Search packages...",
                        modifier = Modifier.padding(top = 7.dp),
                    style = TextStyle(
                        color = LightGreen,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.outfitregular))
                    )
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = null,
                    modifier = Modifier
                        .size(34.dp)
                        .padding(horizontal = 8.dp),
                    tint = LightGreen



                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(68.dp)
                .shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = false
                ),

            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                focusedBorderColor = White,
                unfocusedBorderColor = White
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(55.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundWhite)
        ) {
            item {
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            showDialog = false
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                val model = PackageModel(
                                    packages.value!!.packageId,
                                    packageName,
                                    packageDuration.toInt(),
                                    packageCapacity.toInt(),
                                    packageDifficulty,
                                    packagePrice.toDouble()
                                )
                                packageViewModel.editProduct(model)
                                { success, message ->

                                    if (success) {
                                        showDialog = false
                                    }

                                }
                            }) { Text("Update") }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showDialog = false
                            })
                            {
                                Text("Cancel")
                            }
                        },
                        title = { Text("Update Package") },
                        text = {
                            Column {
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = packageName,
                                    onValueChange = { data ->
                                        packageName = data
                                    },
                                    placeholder = {
                                        Text("e.g. Mt. Manaslu Circuit Trek")
                                    }
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = packageDuration,
                                    onValueChange = { data ->
                                        packageDuration = data
                                    },
                                    placeholder = {
                                        Text("Duration")
                                    }
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = packageCapacity,
                                    onValueChange = { data ->
                                        packageCapacity = data
                                    },
                                    placeholder = {
                                        Text("Capacity")
                                    }
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = packageDifficulty,
                                    onValueChange = { data ->
                                        packageDifficulty = data
                                    },
                                    placeholder = {
                                        Text("e.g.easy, medium hard ")
                                    }
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = packagePrice,
                                    onValueChange = { data ->
                                        packagePrice = data
                                    },
                                    placeholder = {
                                        Text("NPR / $")
                                    }
                                )
                            }
                        }
                    )
                }
            }
            if (loading.value) {
                item {
                    CircularProgressIndicator()
                }
            } else {
                items(allPackages.value!!.size) { index ->
                    val data = allPackages.value!![index]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                            .padding(horizontal = 22.dp)

                    )
                    {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(White)
                                .padding(30.dp)
                        )
                        {
                            Column(
                                modifier = Modifier.weight(1f)
                                    .background(White)
                            ) {
                                Text(
                                    data.packageName,
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = FontFamily(Font(R.font.outfit)),
                                        color = DarkGreen
                                    )
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Row {
                                    Row {
                                        Icon(
                                            painter = painterResource(R.drawable.clock),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                                        Text("${data.packageDuration} Days")
                                    }
                                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                                    Row {
                                        Icon(
                                            painter = painterResource(R.drawable.share),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                                        Text("${data.packageCapacity} Members")
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(data.packageDifficulty)
                                Spacer(modifier = Modifier.height(18.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "${data.packagePrice} NPR",
                                        style = TextStyle(
                                            color = ButtonGreen,
                                            fontFamily = FontFamily(Font(R.font.outfit)),
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                                    Text(
                                        "/person",
                                        style = TextStyle(
                                            color = StrokeGrey,
                                            fontSize = 15.sp,
                                            fontFamily = FontFamily(Font(R.font.inter))
                                        )
                                    )
                                }

                            }
                            Column(
                                verticalArrangement = Arrangement.Top
                            ) {
                                IconButton(onClick = {
                                    showDialog = true
                                    packageViewModel.getPackageById(data.packageId)
                                }) {
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = null,
                                        tint = Color.Green
                                    )
                                }
                                IconButton(onClick = {
                                    packageViewModel.deletePackage(data.packageId) { success, msg ->
                                        if (success) {
                                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

                                        }
                                    }
                                }) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun PreviewExploreScreen(){
    ExploreScreen()
}