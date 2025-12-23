package com.example.trailmate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trailmate.model.PackageModel
import com.example.trailmate.model.UserModel
import com.example.trailmate.repository.PackageRepo

class PackageViewModel(val repo: PackageRepo): ViewModel() {
    fun addPackage(
        model: PackageModel,
        callback: (Boolean, String) -> Unit
    ) {
        repo.addPackage(model, callback)
    }
    fun deletePackage(packageId: String, callback: (Boolean, String) -> Unit) {
        repo.deletePackage(packageId, callback)
    }

    fun editProduct(
        model: PackageModel,
        callback: (Boolean, String) -> Unit
    ) {
        repo.editPackage(model,callback)
    }

    private val _packages = MutableLiveData< PackageModel?>()
    val packages: MutableLiveData<PackageModel?> get() = _packages

    fun getPackageById(packageId: String) {
        repo.getPackageById(packageId) { success, message, data ->
            if (success) {
                _packages.value = data
            } else {
                _packages.value = null
            }
        }
    }

    private val _allPackages = MutableLiveData<List<PackageModel>?>()
    val allPackages: MutableLiveData<List<PackageModel>?> get() = _allPackages

    fun getAllPackage() {
        repo.getAllPackage { success, message, data ->
            if (success) {
                _allPackages.value = data
            } else {
                _allPackages.value = emptyList()
            }
        }
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading: MutableLiveData<Boolean> get() = _loading

}