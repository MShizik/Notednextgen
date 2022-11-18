package com.example.noted.model.auth

import android.content.Context
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

    fun changeRememberedInfo(context : Context){
        val preferencesUserData = context.getSharedPreferences("user", Context.MODE_PRIVATE)
        val preferencesEditor = preferencesUserData.edit()
        preferencesEditor.remove("password")
        preferencesEditor.putString("password", stPasswordUser)
        preferencesEditor.apply()
    }

    fun changeInfoInDatabase(){
        var databaseAuth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance()
        var users = database.reference
        users.child(stEmailUser).child("password").setValue(stPasswordUser)
    }



}