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

    fun changeStep(iStepCount : Int){
        var currentHint : String = "";
        when(iStepCount){
            1->currentHint =.toString()
            2->currentHint =.toString()
            3->currentHint =.toString()
            4->currentHint =.toString()
        }
        setTextMainHint(currentHint)
        if(iStepCount == 4){
            setTextBtnNextInput(R.string.registration_reg_button.toString())
        }
        else{
            setTextBtnNextInput(R.string.registration_next_button.toString())
        }

        setTextMessage(R.string.registration_tv_greeting.toString())
    }

    fun showError(iErrorID : Int){
        when(iErrorID){
            101->setTextErrorMessage(R.string.registration_wrong_email_message.toString())
            102->setTextErrorMessage(R.string.registration_wrong_key_word_message.toString())
            103->setTextErrorMessage(R.string.registration_wrong_password.toString())
            104->setTextErrorMessage(R.string.registration_wrong_repeat_password.toString())
        }
    }

}