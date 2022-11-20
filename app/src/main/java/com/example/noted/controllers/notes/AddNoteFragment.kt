package com.example.noted.controllers.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.noted.R
import com.example.noted.model.auth.UserDataModel
import com.example.noted.model.notes.noteStructure
import com.example.noted.views.notes.AddNotesView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AddNoteFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewAddNote = AddNotesView(view)

        var parentNote = arguments?.getSerializable("currentNode") as noteStructure?
        val dmUser = arguments?.getSerializable("userModel") as UserDataModel
        val btnAdd = view.findViewById(R.id.add_btn_save) as Button

        if (parentNote != null)
            viewAddNote.setParentKey(parentNote.getKey())
        else
            viewAddNote.setParentKey(dmUser.getEmail())



        btnAdd.setOnClickListener{
            var databaseAuth = FirebaseAuth.getInstance()
            var database = FirebaseDatabase.getInstance()
            var user = database.reference.child(dmUser.getEmail()).child("notes")

            if ( parentNote != null )
                for (way in parentNote!!.getWay())
                    user = user.child(way)

            user.child(viewAddNote.getChildKey()).child("CurrentNoteKey").setValue(viewAddNote.getChildValue())
            if (parentNote != null){
                var tmp : noteStructure = noteStructure(
                    key = viewAddNote.getChildKey(),
                    value = viewAddNote.getChildValue()
                )
                parentNote!!.addChildrenNote(tmp)
                tmp.setParent(parentNote!! )
            }
            else
                parentNote = noteStructure(viewAddNote.getChildKey(), viewAddNote.getChildValue())

            var fragmentToChange  = NotesFragment()
            var tmpBundle :  Bundle = Bundle()
            tmpBundle.putSerializable("userModel",dmUser)
            tmpBundle.putSerializable("currentNode",parentNote)
            fragmentToChange.arguments = tmpBundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.notes_fragment_holder,fragmentToChange).commit()
        }
    }

}