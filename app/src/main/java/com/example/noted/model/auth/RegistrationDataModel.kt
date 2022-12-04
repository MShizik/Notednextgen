package com.example.noted.model.auth

import android.content.Context
import android.text.TextUtils.isEmpty
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationDataModel(stKeyWord : String, stEmail : String, stPassword : String) : UserDataModel(stEmail, stPassword){

    protected var stKeyWordUser = stKeyWord

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
        return !isEmpty(stEmailUser)
    }





    fun writeDataToDatabase(){
        var databaseAuth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance()
        var users = database.reference
        users.child(stEmailUser.replace(".","")).child("password").setValue(stPasswordUser)
        users.child(stEmailUser.replace(".","")).child("keyword").setValue(stKeyWordUser)
        users.child(stEmailUser.replace(".","")).child("notes").child("root").child("CurrentKeyValue").setValue("head");
    }
}