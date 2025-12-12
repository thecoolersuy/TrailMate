package com.example.trailmate.repository

import com.example.trailmate.model.PackageModel
import com.example.trailmate.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PackageRepoImpl  : PackageRepo{
    val database :FirebaseDatabase  = FirebaseDatabase.getInstance()

    val ref : DatabaseReference = database.getReference("packages")

    override fun addPackageToDatabase(
        packageId : String,
        model: PackageModel,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(packageId).setValue(model)
            .addOnCompleteListener {
              if(it.isSuccessful){
                  callback(true, "Package added")
              }else{
                  callback(false,"${it.exception?.message}")
              }
        }
    }

    override fun getPackageById(
        packageId: String,
        callback: (Boolean, String, PackageModel?) -> Unit
    ) {
        ref.child(packageId)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val packages = snapshot.getValue(PackageModel:: class.java)
                        if (packages != null){
                            callback(true, "packages fetched", packages)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(false, error.message,null)
                }
            })
    }

}