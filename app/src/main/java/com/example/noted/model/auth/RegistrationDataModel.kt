package com.example.noted.model.auth

import android.text.TextUtils.isEmpty
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationDataModel(){

    private  var stKeyWordUser : String = "";
    private var stPasswordUser : String = ""
    private var stEmailUser : String = ""

    fun setKeyWord( stKeyWord : String){
        stKeyWordUser = stKeyWord
    }

    fun setEmail (stEmailUser: String){
        this.stEmailUser = stEmailUser
    }

    fun setPassword(stPassword: String){
        this.stPasswordUser = stPassword
    }

    fun getPassword():String{
        return this.stPasswordUser
    }

    fun checkKeyWord(stKeyWord: String) : Boolean{
        return stKeyWord.length > 3
    }

    fun checkPassword(stPassword : String) : Boolean{
        var regexSequence = "^.*(?=.{8,})(?=.*[a-zA-Z])(?=.*\\d).*\$".toRegex()
        return regexSequence.matches(stPassword)
    }

    fun checkEmail(stEmail : String) : Boolean{
        return !isEmpty(stEmail) && android.util.Patterns.EMAIL_ADDRESS.matcher(stEmail).matches()
    }

    fun setData(stEmailUser : String, stPasswordUser : String, stKeyWordUser : String) {
        this.stEmailUser = stEmailUser
        this.stPasswordUser = stPasswordUser
        this.stKeyWordUser = stKeyWordUser
    }

    fun createUserDataModel() : UserDataModel{
        return UserDataModel(stEmailUser = stEmailUser, stPasswordUser = stPasswordUser)
    }


    fun writeDataToDatabase(){
        var databaseAuth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance()
        var users = database.reference
        users.child(stEmailUser).child("password").setValue(stPasswordUser)
        users.child(stEmailUser).child("keyword").setValue(stKeyWordUser)
    }
}