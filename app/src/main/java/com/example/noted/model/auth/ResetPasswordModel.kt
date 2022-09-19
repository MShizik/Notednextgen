package com.example.noted.model.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class ResetPasswordModel(private var stKeyWordUser : String,private var stEmailUser : String) : UserDataModel(stEmailUser, "") {

    private lateinit var stPasswordUser : String
    private var bUserValidation : Boolean = false
    private var bPasswordValidation : Boolean = false

    override fun checkUserValidation(databaseUserData: DataSnapshot){
        bUserValidation = ((databaseUserData.value != null) and (databaseUserData.child("keyword").value.toString() == stKeyWordUser) )
    }

    override fun getUserValidation() : Boolean{
        return bUserValidation
    }

    fun changeInfoInDatabase(stPassword : String){
        stPasswordUser = stPassword
        var databaseAuth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance()
        var users = database.reference
        users.child(stEmailUser).child("password").setValue(stPassword)
    }

    fun getUserDataModel() : UserDataModel{
        return UserDataModel(stEmailUser, stPasswordUser)
    }



}