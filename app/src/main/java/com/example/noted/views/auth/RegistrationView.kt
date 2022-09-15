package com.example.noted.views.auth

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.noted.R

class RegistrationView(var rootView: View) {

    var tvMessage : TextView = rootView.findViewById(R.id.registration_tv_message)
    var tvMainHint : TextView = rootView.findViewById(R.id.registration_tv_main_hint)

    var etMainField : EditText = rootView.findViewById(R.id.registration_et_main)

    var btnNextInput : Button = rootView.findViewById(R.id.registration_btn_next)


    fun setTextMessage(message : String){
        tvMessage.text = message
        tvMessage.setTextAppearance(R.style.CustomTextView)
    }

    fun setTextErrorMessage(message: String){
        tvMessage.text = message
        tvMessage.setTextAppearance(R.style.CustomTextViewError)
    }

    fun setTextMainHint(text: String){
        tvMainHint.text = text
    }

    fun getTextMainField() : String{
        return etMainField.text.toString()
    }

    fun setTextMainField(text: String){
        etMainField.setText(text)
    }

    fun setTextBtnNextInput(text: String){
        btnNextInput.text = text
    }

}