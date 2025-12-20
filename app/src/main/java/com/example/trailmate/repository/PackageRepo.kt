package com.example.trailmate.repository

import com.example.trailmate.model.PackageModel
import com.example.trailmate.model.UserModel


interface PackageRepo {
    fun addPackage(
        model: PackageModel,
        callback: (Boolean, String) -> Unit
    )

    fun deletePackage(packageId: String, callback: (Boolean, String) -> Unit)

    fun editPackage(
        packageId: String,
        model: PackageModel,
        callback: (Boolean, String) -> Unit
    )

    fun getAllPackage(callback: (Boolean, String, List<PackageModel>?) -> Unit)

    fun getPackageById(packageId: String, callback: (Boolean, String, PackageModel?) -> Unit)


}