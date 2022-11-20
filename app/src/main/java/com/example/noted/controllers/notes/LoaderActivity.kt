package com.example.noted.controllers.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.noted.R
import com.example.noted.model.auth.UserDataModel
import com.example.noted.model.notes.noteStructure
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class LoaderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)

        var dmUser : UserDataModel? = intent.extras?.getSerializable("userModel") as UserDataModel?

        lateinit var result : DataSnapshot
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("users")
        ref.child(dmUser!!.getEmail()  ).child("notes").get().addOnSuccessListener {
            getUserNotes(dmUser.getRootNote(), it)
        }
        val intentWorkActivity : Intent =  Intent(this, NotesActivity::class.java)
        .putExtra("userModel", dmUser)
        startActivity(intentWorkActivity)
    }

    fun getUserNotes(rootNote : noteStructure, ref : DataSnapshot){
        for(i in ref.children){
            if(i.key.toString().equals("CurrentKeyValue")) continue
            var tmpNote : noteStructure = noteStructure(i.key.toString(),i.child("CurrentKeyValue").value.toString())
            tmpNote.setParent(rootNote)
            rootNote.addChildrenNote(tmpNote)
            if (i.hasChildren()){
                getUserNotes(tmpNote, i)
            }
        }
    }
}