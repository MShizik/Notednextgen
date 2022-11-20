package com.example.noted.views.auth

import android.content.res.Resources
import android.provider.Settings.Global.getString
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.noted.R

class RegistrationView(var rootView: View, private val resources: Resources) {

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

    fun changeStep(iStepCount : Int){
        var currentHint : String = "";
        when(iStepCount){
            1->currentHint = resources.getString(R.string.registration_email_hint).toString()
            2->currentHint = resources.getString(R.string.registration_key_word_hint)
            3->currentHint = resources.getString(R.string.registration_password_hint)
            4->currentHint = resources.getString(R.string.registration_repeat_password_hint)
        }
        setTextMainHint(currentHint)
        if(iStepCount == 4){
            setTextBtnNextInput(resources.getString(R.string.registration_reg_button))
        }
        else{
            setTextBtnNextInput(resources.getString(R.string.registration_next_button))
        }
        setTextMainField("")
        setTextMessage(resources.getString(R.string.registration_tv_greeting))
    }

    fun showError(iErrorID : Int){
        when(iErrorID){
            101->setTextErrorMessage(resources.getString(R.string.registration_wrong_email_message))
            102->setTextErrorMessage(resources.getString(R.string.registration_wrong_key_word_message))
            103->setTextErrorMessage(resources.getString(R.string.registration_wrong_password))
            104->setTextErrorMessage(resources.getString(R.string.registration_wrong_repeat_password))
        }
    }

}