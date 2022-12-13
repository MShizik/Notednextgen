package com.example.noted.controllers.notes

import ProductsListAdapter
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.noted.R
import com.example.noted.model.auth.UserDataModel
import com.example.noted.model.notes.noteStructure
import com.example.noted.views.notes.NotesListView
import java.util.jar.Attributes

class NotesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dmUser = arguments?.getSerializable("userModel") as UserDataModel
        var currentNode : noteStructure?  = arguments?.getSerializable("nextNode") as noteStructure?
        val viewNotesView : NotesListView = NotesListView(view);

        lateinit var adapter : ProductsListAdapter
        val lvNotesList : ListView = view.findViewById(R.id.notes_lv_info)

        if (currentNode != null) {

            viewNotesView.setDirectoryWay(currentNode.getKey())
            viewNotesView.startLoadingAnimation()
            adapter = ProductsListAdapter(requireContext(), currentNode.getAllChildren());
            lvNotesList.adapter = adapter
            viewNotesView.endLoadingAnimation()

        }else{
            viewNotesView.setDirectoryWay(dmUser.getEmail())
            adapter = ProductsListAdapter(requireContext(), ArrayList<noteStructure>());
            lvNotesList.adapter = adapter
            viewNotesView.endLoadingAnimation()
        }

        val btnAddNewNote = view.findViewById(R.id.notes_btn_add) as Button
        val btnBack = view.findViewById(R.id.notes_btn_back) as Button

        btnBack.setOnClickListener{
            if (currentNode == null || currentNode?.getParent() == null){
                var intentToLoginActivity = Intent(this.requireContext(), LoaderActivity::class.java)
                intentToLoginActivity.putExtra("userModel", dmUser)
                startActivity(intentToLoginActivity)
            }
            else{
                currentNode = currentNode?.getParent()
                adapter.notifyDataSetChanged()
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
    }
}