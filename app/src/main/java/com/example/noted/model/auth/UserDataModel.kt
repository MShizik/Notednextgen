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

open class UserDataModel(stEmail : String, stPassword : String) : Serializable{

    protected var stEmailUser = stEmail.replace(".","").replace(" ","")
    protected var stPasswordUser = stPassword


    private var userPasswordValidation = false
    private var userEmailValidation = false

    private  var noteRoot : noteStructure? = null

    fun setEmail(stEmail : String){
        stEmailUser = stEmail.replace(".","").replace(" ","")
    }

    fun setPassword( stPassword : String){
        stPasswordUser = stPassword
    }

    fun getEmail() : String{
       return stEmailUser
    }

    fun getRootNote()  : noteStructure?{
        return noteRoot
    }

    open fun checkUserValidation(databaseUserData: DataSnapshot) {
        userPasswordValidation = databaseUserData.child("password").value.toString() == stPasswordUser
    }

    fun saveUserData(context : Context){
        val preferencesUserData = context.getSharedPreferences("user", Context.MODE_PRIVATE)
        val preferencesEditor = preferencesUserData.edit()
        preferencesEditor.putString("email", stEmailUser)
        preferencesEditor.putString("password", stPasswordUser)
        preferencesEditor.apply()
    }

    fun getPassValid() : Boolean{
        return userPasswordValidation;
    }

    fun getEmailValid() : Boolean {
        return userEmailValidation
    }

    fun setPassValid(valid : Boolean){
        userPasswordValidation = valid
    }

    fun setEmailValid(valid : Boolean){
        userEmailValidation = valid
    }

    fun setRootNote(rootNote : noteStructure){
        this.noteRoot = rootNote;
    }




}