package com.example.trailmate.repository

import com.example.trailmate.model.PackageModel
import com.example.trailmate.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PackageRepoImpl  : PackageRepo{
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


}