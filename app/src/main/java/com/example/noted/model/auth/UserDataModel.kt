package com.example.noted.model.auth

import android.content.Context
import com.example.noted.model.notes.noteStructure
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import java.io.Serializable

open class UserDataModel(private var stEmailUser : String, private var stPasswordUser : String) : Serializable{

    var userValidation = false

    private lateinit var noteRoot : noteStructure

    fun setEmail(stEmail : String){
        stEmailUser = stEmail
    }

    fun setPassword( stPassword : String){
        stPasswordUser = stPassword
    }

    fun getEmail() : String{
       return stEmailUser
    }

    fun getRootNote()  : noteStructure{
        return noteRoot
    }

    open fun checkUserValidation(databaseUserData: DataSnapshot) {
        userValidation = ( (databaseUserData.value != null) and (databaseUserData.child("password").value.toString() == stPasswordUser) )
    }

    fun saveUserData(context : Context){
        val preferencesUserData = context.getSharedPreferences("user", Context.MODE_PRIVATE)
        val preferencesEditor = preferencesUserData.edit()
        preferencesEditor.putString("email", stEmailUser)
        preferencesEditor.putString("password", stPasswordUser)
        preferencesEditor.apply()
    }




}