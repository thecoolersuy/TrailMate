package com.example.trailmate.repository

import com.example.trailmate.model.PackageModel
import com.example.trailmate.model.UserModel


interface PackageRepo {

    fun addPackageToDatabase(
        packageId: String,
        model: PackageModel,
        callback: (Boolean, String) -> Unit
    )
    fun getPackageById(
        packageId: String,
        callback: (Boolean, String, PackageModel?) -> Unit
    )

}