package com.example.noted.views.notes

import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.noted.R

class ChangeNoteView (var rootView : View) {
    var tvParentKey : TextView = rootView.findViewById(R.id.add_tv_parent_key)
    var tvChildKeyHint : TextView = rootView.findViewById(R.id.add_tv_child_key_hint)
    var tvChildValueHint : TextView = rootView.findViewById(R.id.add_tv_child_value_hint)

    var etChildKey : EditText = rootView.findViewById(R.id.add_et_child_key)
    var etChildValue : EditText = rootView.findViewById(R.id.add_et_child_value)


    fun setParentKey(key : String){
        tvParentKey.text = key.takeIf { key.length < 18 } ?: (key.substring(0, 18) + "...")
    }

    fun setChildKey(key : String){
        etChildKey.setText(key)
    }

    fun getChildKey()  : String{
        return etChildKey.text.toString()
    }

    fun setChildValue(value : String) {
        etChildValue.setText(value)
    }

    fun getChildValue() : String{
        return etChildValue.text.toString()
    }
}