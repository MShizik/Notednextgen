package com.example.noted.controllers.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.example.noted.R
import com.example.noted.model.auth.UserDataModel
import com.example.noted.model.notes.noteStructure
import com.example.noted.views.notes.ChangeNoteView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ChangeNoteFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewChangeNote = ChangeNoteView(view)

        var currentNote = arguments?.getSerializable("currentNode") as noteStructure?

        val dmUser = arguments?.getSerializable("userModel") as UserDataModel
        val btnSave = view.findViewById(R.id.add_btn_save) as Button
        val btnBack = view.findViewById(R.id.add_btn_back) as ImageButton

        if (currentNote!!.getParent() != null)
            viewChangeNote.setParentKey(if (currentNote.getParent()!!.getKey() != "root")  currentNote.getParent()!!.getKey() else resources.getString(
                R.string.word_user))
        else
            viewChangeNote.setParentKey(dmUser.getEmail())

        viewChangeNote.setChildKey(currentNote.getKey())
        viewChangeNote.setChildValue(currentNote.getValue())



        btnSave.setOnClickListener{
            var databaseAuth = FirebaseAuth.getInstance()
            var database = FirebaseDatabase.getInstance()
            var user = database.reference.child(dmUser.getEmail()).child("notes")

            if ( currentNote.getParent() != null ) {
                for (way in currentNote!!.getWay())
                    user = user.child(way)
            }
            user.child(currentNote!!.getKey()).setValue(null)

            currentNote.setKey(viewChangeNote.getChildKey())
            currentNote.setValue(viewChangeNote.getChildValue())

            writeNoteToDatabase(user, currentNote)


            var fragmentToChange  = NotesFragment()
            var tmpBundle :  Bundle = Bundle()
            tmpBundle.putSerializable("userModel",dmUser)
            tmpBundle.putSerializable("nextNode",currentNote)
            fragmentToChange.arguments = tmpBundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.notes_fragment_holder,fragmentToChange).commit()
        }

        btnBack.setOnClickListener {
            var fragmentToChange  = NotesFragment()
            var tmpBundle :  Bundle = Bundle()
            tmpBundle.putSerializable("userModel",dmUser)
            tmpBundle.putSerializable("nextNode",currentNote)
            fragmentToChange.arguments = tmpBundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.notes_fragment_holder,fragmentToChange).commit()
        }
    }

    fun writeNoteToDatabase(place : DatabaseReference, currentNote : noteStructure){
        place.child(currentNote.getKey()).child("CurrentNoteKey").setValue(currentNote.getValue())
        for(child : noteStructure in currentNote.getAllChildren())
            writeNoteToDatabase(place.child(currentNote.getKey()), child)
    }
}