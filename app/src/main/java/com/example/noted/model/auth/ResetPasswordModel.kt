package com.example.noted.model.auth

class ResetPasswordModel(private var stKeyWordUser : String,private var stEmailUser : String, private var stPasswordUser : String) : UserDataModel(stEmailUser, stPasswordUser) {



}