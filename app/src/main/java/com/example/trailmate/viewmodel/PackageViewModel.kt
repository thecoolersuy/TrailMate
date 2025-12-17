package com.example.trailmate.viewmodel

import androidx.lifecycle.ViewModel
import com.example.trailmate.model.PackageModel
import com.example.trailmate.model.UserModel
import com.example.trailmate.repository.PackageRepo

class PackageViewModel(val repo: PackageRepo): ViewModel() {

    fun addPackageToDatabase(
        packageId: String,
        model: PackageModel,
        callback: (Boolean, String) -> Unit
    ){
        repo.addPackageToDatabase(packageId, model,callback)
    }
}