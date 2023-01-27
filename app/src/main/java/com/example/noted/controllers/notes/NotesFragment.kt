package com.example.noted.controllers.notes

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.noted.R
import com.example.noted.controllers.auth.AuthActivity
import com.example.noted.model.auth.UserDataModel
import com.example.noted.model.notes.noteStructure
import com.example.noted.views.notes.NotesListView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


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
                requireContext().getSharedPreferences("user", 0).edit().clear().commit()
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
            Handler(Looper.getMainLooper()).post {
                val dialogDeleteFriend = Dialog(requireContext())
                var dialogContentView : View = View.inflate(this.requireContext(), R.layout.dialog_delete_block, null);

                val model = ShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, 32.0f)
                    .build()
                val shape = MaterialShapeDrawable(model)
                shape.fillColor = ContextCompat.getColorStateList(this.requireContext(), R.color.white)
                ViewCompat.setBackground(dialogContentView, shape)
                dialogDeleteFriend.setContentView(R.layout.dialog_delete_block)
                dialogDeleteFriend.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val btnDeleteAccept =
                    dialogDeleteFriend.findViewById(R.id.dialog_delete_accept_btn) as Button
                val btnDeclineDelete =
                    dialogDeleteFriend.findViewById(R.id.dialog_delete_decline_btn) as Button

                btnDeclineDelete.setOnClickListener {
                    dialogDeleteFriend.cancel()
                }
                btnDeleteAccept.setOnClickListener {
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
                    dialogDeleteFriend.cancel()
                }
                dialogDeleteFriend.setCancelable(true)
                dialogDeleteFriend.show()
            }

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