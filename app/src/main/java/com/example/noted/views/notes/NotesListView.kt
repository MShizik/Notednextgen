package com.example.noted.views.notes

import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.noted.R

class NotesListView(var rootView : View) {

    var tvDirectory : TextView = rootView.findViewById(R.id.notes_tv_directory_name)

    var lvNotesList : ListView = rootView.findViewById(R.id.notes_lv_info)

    var pbNotesLoader : ProgressBar = rootView.findViewById(R.id.notes_progress_bar)


    fun setDirectoryWay( way : String){
        tvDirectory.text = way
    }

    fun setStartPosition(){
        lvNotesList.visibility = View.VISIBLE
        lvNotesList.alpha = 0F
        pbNotesLoader.visibility = View.VISIBLE
        pbNotesLoader.translationX = ((rootView.width+100) * -1).toFloat()
    }

    fun startLoadingAnimation(){
        pbNotesLoader.animate().setDuration(500L).translationX(0F).setListener(null)
    }

    fun endLoadingAnimation(){
        pbNotesLoader.animate().setDuration(500L).translationX((rootView.width+100).toFloat()).withEndAction{pbNotesLoader.visibility = View.GONE}
        lvNotesList.animate().setDuration(500L).alpha(1.0F).setListener(null)
    }

}