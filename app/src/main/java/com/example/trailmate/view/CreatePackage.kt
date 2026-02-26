package com.example.trailmate.view

import android.app.Activity
import android.net.Uri
import com.example.trailmate.R
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.trailmate.model.PackageModel
import com.example.trailmate.repository.PackageRepoImpl
import com.example.trailmate.ui.theme.ButtonGreen
import com.example.trailmate.ui.theme.DarkGreen
import com.example.trailmate.utils.ImageUtils
import com.example.trailmate.viewmodel.PackageViewModel


  class CreatePackage : ComponentActivity() {
      lateinit var imageUtils: ImageUtils
      var selectedImageUri by mutableStateOf<Uri?>(null)
      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          enableEdgeToEdge()
          imageUtils = ImageUtils(this, this)
          imageUtils.registerLaunchers { uri ->
              selectedImageUri = uri
          }
          setContent {
              CreatePackageBody(
                  selectedImageUri = selectedImageUri,
                  onPickImage = { imageUtils.launchImagePicker() }
              )
          }

      }
  }



    @Composable
    fun CreatePackageBody(selectedImageUri: Uri?, onPickImage: () -> Unit) {

        var packageName by remember { mutableStateOf("") }
        var packageDuration by remember { mutableStateOf("") }
        var packageCapacity by remember { mutableStateOf("") }
        var packageDifficulty by remember { mutableStateOf("") }
        var packagePrice by remember { mutableStateOf("") }
        var image by remember { mutableStateOf("") }

        val context = LocalContext.current;
        val activity = context as? Activity
        val packageViewModel = remember { PackageViewModel(PackageRepoImpl()) }

        Scaffold(

        ) { padding ->


            Column(
                modifier = Modifier.padding(padding)
                    .padding(50.dp),

                ) {
                Text(
                    "Create Package",
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = DarkGreen,
                        textAlign = TextAlign.Left,
                        fontFamily = FontFamily(Font(com.example.trailmate.R.font.outfit))
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onPickImage()
                        }
                        .padding(10.dp)
                ) {
                    if (selectedImageUri != null) {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painterResource(R.drawable.imageplaceholder),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
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
                Spacer(modifier = Modifier.height(20.dp))
                Button(
//
                    onClick = {

                        if (selectedImageUri != null) {
                            Toast.makeText(context, "Uploading image...", Toast.LENGTH_SHORT).show()

                            packageViewModel.uploadImage(context, selectedImageUri) { imageUrl ->
                                if (imageUrl != null) {
                                    Toast.makeText(context, "Image uploaded: $imageUrl", Toast.LENGTH_SHORT).show()

                                    val model = PackageModel(
                                        "",
                                        packageName = packageName,
                                        packageDuration = packageDuration.toIntOrNull() ?: 0,
                                        packageCapacity = packageCapacity.toIntOrNull() ?: 0,
                                        packageDifficulty = packageDifficulty,
                                        packagePrice = packagePrice.toDoubleOrNull() ?: 0.0,
                                        image = imageUrl
                                    )

                                    Toast.makeText(context, "Saving package...", Toast.LENGTH_SHORT).show()

                                    packageViewModel.addPackage(model) { success, message ->
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                        if (success) activity?.finish()
                                    }
                                } else {
                                    Toast.makeText(context, "Image upload failed!", Toast.LENGTH_LONG).show()
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please select an image first", Toast.LENGTH_SHORT).show()
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
    CreatePackageBody(
        selectedImageUri = null, // or pass a mock Uri if needed
        onPickImage = {} // no-op
    )
}



