package com.example.noted.model.notes

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class noteStructure(private var key : String, private var value : String) : Serializable {

    private var noteParent : noteStructure? = null

    private var alChildNotes : ArrayList<noteStructure> = ArrayList()

    fun setParent ( parent : noteStructure){
        this.noteParent = parent
    }

    fun getParent() : noteStructure? {
        return this.noteParent
    }

    fun getKey() : String{
        return key;
    }

    fun getValue() : String{
        return value
    }

    fun setKey( key : String ){
        this.key = key
    }

    fun setValue( value : String){
        this.value = value
    }

    fun deleteChildAt(index: Int){
        alChildNotes.removeAt(index)
    }

    fun getChildAt(index : Int) : noteStructure{
        return alChildNotes[index]
    }

    fun getAllChildren() : ArrayList<noteStructure>{
        return alChildNotes
    }

    fun addChildrenNote(noteStructure: noteStructure){
        alChildNotes.add(noteStructure)
    }

    fun getWay():ArrayList<String>{
        var tmp = this.noteParent
        var alTmp = ArrayList<String>()
        while(tmp != null){
            alTmp.add(tmp.getKey())
            tmp = tmp.noteParent
        }
        alTmp.reverse();
        return alTmp
    }

}