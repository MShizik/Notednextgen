package com.example.noted.views.auth

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.noted.R

class ForgetPasswordView(var rootView : View, var context:Context) {

    private var tvMessage : TextView = rootView.findViewById(R.id.reset_tv_greeting)
    private var tvFirstHint : TextView = rootView.findViewById(R.id.reset_tv_email_hint)
    private var tvSecondHint : TextView = rootView.findViewById(R.id.reset_tv_keyword_hint)

    private var etFirstField : EditText = rootView.findViewById(R.id.reset_et_email)
    private var etSecondField : EditText = rootView.findViewById(R.id.reset_et_keyword)

    private var btnNext : Button = rootView.findViewById(R.id.reset_btn_reset)




    fun setTextMessage(message : String){
        tvMessage.text = message
        tvMessage.setTextAppearance(R.style.CustomTextView)
    }

    fun setTextErrorMessage(message: String){
        tvMessage.text = message
        tvMessage.setTextAppearance(R.style.CustomTextViewError)
    }

    fun setTextFirstHint(hint : String){
        tvFirstHint.text = hint
    }

    fun setTextFirstField(text : String){
        etFirstField.setText(text)
    }

    fun getTextFirstField() : String{
        return etFirstField.text.toString()
    }

    fun setTextSecondHint(hint : String){
        tvSecondHint.text = hint
    }

    fun setTextSecondField(text : String){
        etSecondField.setText(text)
    }

    fun getTextSecondField() : String {
        return etSecondField.text.toString()
    }

    fun setTextBtnNext(text : String){
        btnNext.text = text
    }

    fun changeToNextStep(){
        setTextBtnNext(context.resources.getString(R.string.reset_change_button))
        setTextFirstHint(context.resources.getString(R.string.reset_password_hint))
        setTextSecondHint(context.resources.getString(R.string.reset_repeat_password_hint))
        setTextMessage(context.resources.getString(R.string.reset_greeting_message))
        setTextFirstField("");
        setTextSecondField("")
    }

    fun changeToBackStep(){
        setTextBtnNext(context.resources.getString(R.string.reset_next_button))
        setTextFirstHint(context.resources.getString(R.string.reset_email_hint))
        setTextSecondHint(context.resources.getString(R.string.reset_keyword_hint))
        setTextMessage(context.resources.getString(R.string.reset_greeting_message))
        setTextFirstField("");
        setTextSecondField("")
    }

    fun showBaseErrorMessage(){
        setTextErrorMessage(context.resources.getString(R.string.reset_base_error_message))
        setTextFirstField("");
        setTextSecondField("")
    }

    fun showPasswordShortErrorMessage(){
        setTextErrorMessage(context.resources.getString(R.string.reset_password_quality_message))
    }

    fun showPasswordMatchErrorMessage(){
        setTextErrorMessage(context.resources.getString(R.string.reset_passwords_match_message))
    }



}