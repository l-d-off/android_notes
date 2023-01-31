package com.darf.android.uni1

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment

class UpdateNoteFragment(
    private val fragment: TabFragment,
    private val note: Note
) :
    DialogFragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_update_note, container) // null
        val updateNoteText = v.findViewById<EditText>(R.id.update_note_text)
        updateNoteText.setText(note.note_text)
        v.findViewById<Button>(R.id.update_note_button).setOnClickListener {
            val newText = updateNoteText.text.toString()
            fragment.updateItemClick(note, newText)
            dismiss()
        }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
    }
}