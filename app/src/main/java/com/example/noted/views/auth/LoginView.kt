package com.example.noted.views.auth

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.noted.R


class LoginView(var rootView : View) {

    private var tvMessage : TextView = rootView.findViewById(R.id.login_tv_greeting)
    private var tvEmailHint : TextView = rootView.findViewById(R.id.login_tv_email_hint)
    private var tvPasswordHint : TextView = rootView.findViewById(R.id.login_tv_password_hint)
    private var tvForgetPasswordHint : TextView = rootView.findViewById(R.id.login_tv_forget_password)
    private var tvSignUpHint : TextView = rootView.findViewById(R.id.login_tv_signup)

    private var etEmailField : EditText = rootView.findViewById(R.id.login_et_email)
    private var etPasswordField : EditText = rootView.findViewById(R.id.login_et_password)



    fun setTextMessage(message : String) {
        tvMessage.text = message
        tvMessage.setTextAppearance(R.style.CustomTextView)
    }

    fun setTextErrorMessage(message: String){
        tvMessage.text = message
        tvMessage.setTextAppearance(R.style.CustomTextViewError)
    }

    fun getTextEmailField() : String{
        return etEmailField.text.toString()
    }

    fun setTextEmailField ( message: String ){
        etEmailField.setText(message)
    }

    fun getTextPasswordField() : String {
        return  etPasswordField.text.toString()
    }

    fun setTextPasswordField( message : String ) {
        etPasswordField.setText(message)
    }



}