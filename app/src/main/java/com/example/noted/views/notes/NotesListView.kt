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


}