package com.example.noted.controllers.notes


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.noted.R
import com.example.noted.model.notes.noteStructure

class notesAdapter(private var context: Context, private var items: ArrayList<noteStructure>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txtKey: TextView? = null
        var txtValue: TextView? = null

        init {
            this.txtKey = row?.findViewById<TextView>(R.id.list_row_tv_key)
            this.txtValue = row?.findViewById<TextView>(R.id.list_row_tv_value)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.notes_list_row, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var note = items[position]
        viewHolder.txtKey?.text = note.getKey()
        viewHolder.txtValue?.text = note.getValue()

        return view as View
    }

    override fun getItem(i: Int): noteStructure {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }


}

