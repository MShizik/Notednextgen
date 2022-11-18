package com.example.noted.model.auth

import android.text.TextUtils.isEmpty
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationDataModel(private var stKeyWordUser : String,private var stEmailUser : String, private var stPasswordUser : String) : UserDataModel(stEmailUser, stPasswordUser){

    fun setKeyWord( stKeyWord : String){
        stKeyWordUser = stKeyWord
    }

    fun checkKeyWord() : Boolean{
        return stKeyWordUser.length > 3
    }

    fun checkPassword() : Boolean{
        var regexSequence = "^.*(?=.{8,})(?=.*[a-zA-Z])(?=.*\\d).*\$".toRegex()
        return regexSequence.matches(stPasswordUser)
    }

    fun checkRepeatPassword(stRepeatedPassword : String) : Boolean{
        return stRepeatedPassword == stPasswordUser
    }

    fun checkEmail() : Boolean{
        return !isEmpty(stEmailUser) && android.util.Patterns.EMAIL_ADDRESS.matcher(stEmailUser).matches()
    }


    fun writeDataToDatabase(){
        var databaseAuth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance()
        var users = database.reference
        users.child(stEmailUser).child("password").setValue(stPasswordUser)
        users.child(stEmailUser).child("keyword").setValue(stKeyWordUser)
    }
}