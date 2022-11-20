package com.example.noted.controllers.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.noted.R
import com.example.noted.model.auth.UserDataModel

class NotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        var dmUser : UserDataModel = intent.extras?.getSerializable("userModel") as UserDataModel

        var fragmentToChange  = NotesFragment()
        var tmpBundle :  Bundle = Bundle()
        tmpBundle.putSerializable("userModel",dmUser)
        tmpBundle.putSerializable("nextNode", dmUser.getRootNote())
        fragmentToChange.arguments = tmpBundle
        supportFragmentManager.beginTransaction().replace(R.id.notes_fragment_holder,fragmentToChange).commit()
    }
}