package com.example.noted.controllers.notes

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.core.widget.addTextChangedListener
import com.example.noted.R
import com.example.noted.controllers.auth.AuthActivity
import com.example.noted.model.auth.UserDataModel
import com.example.noted.model.notes.noteStructure
import com.example.noted.views.auth.LoginView
import com.example.noted.views.notes.NotesListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.jar.Attributes

class NotesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dmUser = arguments?.getSerializable("userModel") as UserDataModel
        var currentNode : noteStructure?  = arguments?.getSerializable("nextNode") as noteStructure?
        val viewNotesView : NotesListView = NotesListView(view)
        val etSearch : EditText = view.findViewById(R.id.notes_et_search)

        lateinit var adapter : notesAdapter
        val lvNotesList : ListView = view.findViewById(R.id.notes_lv_info)

        viewNotesView.setStartPosition()
        viewNotesView.startLoadingAnimation()

        if (currentNode != null) {

            viewNotesView.setDirectoryWay(if (currentNode.getKey() != "root")  currentNode.getKey() else resources.getString(R.string.word_user))

            adapter = notesAdapter(requireContext(), currentNode.getAllChildren());
            lvNotesList.adapter = adapter
            viewNotesView.endLoadingAnimation()
        }
        else{
            viewNotesView.setDirectoryWay(dmUser.getEmail())
            adapter = notesAdapter(requireContext(), ArrayList<noteStructure>());
            lvNotesList.adapter = adapter
            viewNotesView.endLoadingAnimation()
        }

        val btnAddNewNote = view.findViewById(R.id.notes_btn_add) as Button
        val btnBack = view.findViewById(R.id.notes_btn_back) as Button

        btnBack.setOnClickListener{
            if (currentNode == null || currentNode?.getParent() == null){
                var intentToLoginActivity = Intent(this.requireContext(), AuthActivity::class.java)
                startActivity(intentToLoginActivity)
            }
            else{
                currentNode = currentNode?.getParent()
                viewNotesView.setDirectoryWay(if (currentNode!!.getKey() != "root")  currentNode!!.getKey() else resources.getString(R.string.word_user))
                adapter = notesAdapter(requireContext(), currentNode!!.getAllChildren());
                lvNotesList.adapter = adapter
            }
        }

        btnAddNewNote.setOnClickListener {
            var fragmentToChange  = AddNoteFragment()
            var tmpBundle :  Bundle = Bundle()
            tmpBundle.putSerializable("userModel",dmUser)
            tmpBundle.putSerializable("currentNode",currentNode)
            fragmentToChange.arguments = tmpBundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.notes_fragment_holder,fragmentToChange).commit()
        }

        lvNotesList.setOnItemClickListener { adapterView, view, position, id ->
            val element : noteStructure = currentNode!!.getAllChildren()[position]
            var fragmentToChange  = NotesFragment()
            var tmpBundle :  Bundle = Bundle()
            tmpBundle.putSerializable("userModel",dmUser)
            tmpBundle.putSerializable("nextNode",element)
            fragmentToChange.arguments = tmpBundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.notes_fragment_holder,fragmentToChange).commit()
        }

        lvNotesList.setOnItemLongClickListener { parent, view, position, id ->
            var element  = currentNode!!.getAllChildren().removeAt(position)
            var databaseAuth = FirebaseAuth.getInstance()
            var database = FirebaseDatabase.getInstance()
            var user = database.reference.child(dmUser.getEmail()).child("notes")

            if ( element != null ) {
                for (way in element!!.getWay())
                    user = user.child(way)
                user = user.child(element!!.getKey())
            }
            user.setValue(null)
            adapter.notifyDataSetChanged()
            true
        }

        etSearch.addTextChangedListener {
            viewNotesView.setStartPosition()
            viewNotesView.startLoadingAnimation()
            if(!etSearch.text.equals("")) {
                adapter = notesAdapter(requireContext(), currentNode!!.getAllChildren().filter { note -> note.getKey().contains(viewNotesView.getSearchText(), ignoreCase = true) ||  note.getValue().contains(viewNotesView.getSearchText(), ignoreCase = true) } as ArrayList<noteStructure> /* = java.util.ArrayList<com.example.noted.model.notes.noteStructure> */)
            }else{
                adapter = notesAdapter(requireContext(), currentNode!!.getAllChildren())
            }

            lvNotesList.adapter = adapter
            viewNotesView.endLoadingAnimation()
        }
    }
}