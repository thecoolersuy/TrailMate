package com.example.trailmate.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.trailmate.model.PackageModel
import com.example.trailmate.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.InputStream
import java.util.concurrent.Executors

import android.os.Handler
import android.os.Looper
import android.provider.OpenableColumns

class PackageRepoImpl  : PackageRepo{
    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to "dj05mkkus",
            "api_key" to "679433492413949",
            "api_secret" to "orUBQ1xeBRF0B8HijZtYouDJ7Bc"
        )
    )
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    val ref: DatabaseReference = database.getReference("packages")


    override fun addPackage(
        model: PackageModel,
        callback: (Boolean, String) -> Unit
    ) {
        val id = ref.push().key.toString()
        model.packageId = id

        ref.child(id).setValue(model).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Package added successfully")
            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun deletePackage(
        packageId: String,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(packageId).removeValue().addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Package deleted successfully")
                } else {
                    callback(false, "${it.exception?.message}")

                }
            }
    }

    override fun editPackage(
        model: PackageModel,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(model.packageId).updateChildren(model.toMap()).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Package updated successfully")
            } else {
                callback(false, "${it.exception?.message}")

            }
        }
    }

    override fun getAllPackage(callback: (Boolean, String, List<PackageModel>?) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var allPackage = mutableListOf<PackageModel>()
                    for (data in snapshot.children) {
                        val packages = data.getValue(PackageModel::class.java)
                        if (packages != null) {
                            allPackage.add(packages)
                        }
                    }
                    callback(true, "Packages fetched successfully", allPackage)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, error.message, emptyList())
            }

        })
    }

    override fun getPackageById(
        packageId: String,
        callback: (Boolean, String, PackageModel?) -> Unit
    ) {
        ref.child(packageId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val packages = snapshot.getValue(PackageModel::class.java)
                    if (packages != null) {
                        callback(true, "Packages fetched successfully", packages)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, error.message, null)
            }
        })
    }

    override fun uploadImage(
        context: Context,
        imageUri: Uri,
        callback: (String?) -> Unit
    ) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
                var fileName = getFileNameFromURI(context, imageUri)

                fileName = fileName?.substringBeforeLast(".") ?: "uploaded_image"

                val response = cloudinary.uploader().upload(
                    inputStream, ObjectUtils.asMap(
                        "public_id", fileName,
                        "resource_type", "image"
                    )
                )

                var imageUrl = response["url"] as String?

                imageUrl = imageUrl?.replace("http://", "https://")

                Handler(Looper.getMainLooper()).post {
                    callback(imageUrl)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    callback(null)
                }
            }
        }
    }

    override fun getFileNameFromURI(
        context: Context,
        imageUri: Uri
    ): String? {
        var fileName: String? = null
        val cursor: Cursor? = context.contentResolver.query(imageUri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        return fileName
    }



}