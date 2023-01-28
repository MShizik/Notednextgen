package com.example.noted.views.notes

import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.noted.R

class NotesListView(var rootView : View) {

    var tvDirectory : TextView = rootView.findViewById(R.id.notes_tv_directory_name)

    var lvNotesList : ListView = rootView.findViewById(R.id.notes_lv_info)

    var pbNotesLoader : ProgressBar = rootView.findViewById(R.id.notes_progress_bar)

    var etSearch : EditText = rootView.findViewById(R.id.notes_et_search)

    fun getSearchText() : String{
        return etSearch.text.toString()
    }

    fun setDirectoryWay( way : String){
        tvDirectory.text = way.takeIf { way.length <= 20 } ?: (way.substring(0, 16) + "...")
    }

    fun setStartPosition(){
        lvNotesList.visibility = View.VISIBLE
        lvNotesList.alpha = 0F
        pbNotesLoader.visibility = View.VISIBLE
        pbNotesLoader.alpha = 1F
    }

    fun startLoadingAnimation(){
        pbNotesLoader.animate().setDuration(2500L).alpha(1F).setListener(null)
    }

    fun endLoadingAnimation(){
        pbNotesLoader.animate().setDuration(1500L).alpha(0F).withEndAction{pbNotesLoader.visibility = View.GONE}
        lvNotesList.animate().setDuration(2500L).alpha(1.0F).setListener(null)
    }

}