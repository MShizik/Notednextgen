package com.example.noted.model.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import java.io.Serializable

open class UserDataModel(private var stEmailUser : String, private var stPasswordUser : String) : Serializable{

    var userValidation : Boolean = false

    fun setEmail(stEmail : String){
        stEmailUser = stEmail
    }

    fun setPassword( stPassword : String){
        stPasswordUser = stPassword
    }

    fun getEmail() : String{
       return stEmailUser
    }

    open fun checkUserValidation(databaseUserData: DataSnapshot) {
        userValidation = ( (databaseUserData.value != null) and (databaseUserData.child("password").value.toString() == stPasswordUser) )
    }



}