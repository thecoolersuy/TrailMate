package com.example.trailmate.repository

import android.content.Context
import android.net.Uri
import com.example.trailmate.model.PackageModel
import com.example.trailmate.model.UserModel


interface PackageRepo {
    fun addPackage(
        model: PackageModel,
        callback: (Boolean, String) -> Unit
    )

    fun deletePackage(packageId: String, callback: (Boolean, String) -> Unit)

    fun editPackage(
        model: PackageModel,
        callback: (Boolean, String) -> Unit
    )

    fun getAllPackage(callback: (Boolean, String, List<PackageModel>?) -> Unit)

    fun getPackageById(packageId: String, callback: (Boolean, String, PackageModel?) -> Unit)

    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit)

    fun getFileNameFromURI(context: Context,imageUri: Uri) : String?

}