package com.example.noted.model.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class ResetPasswordModel(private var stKeyWordUser : String, private var stEmailUser : String, private var stPasswordUser : String) : UserDataModel(stEmailUser, stPasswordUser) {

    override fun checkUserValidation(databaseUserData: DataSnapshot) {
        userValidation = ((databaseUserData.value != null) and (databaseUserData.child("keyword").value.toString() == stKeyWordUser) )
    }

    fun checkPassword() : Boolean{
        var regexSequence = "^.*(?=.{8,})(?=.*[a-zA-Z])(?=.*\\d).*\$".toRegex()
        return regexSequence.matches(stPasswordUser)
    }

    fun changeInfoInDatabase(){
        var databaseAuth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance()
        var users = database.reference
        users.child(stEmailUser).child("password").setValue(stPasswordUser)
    }



}