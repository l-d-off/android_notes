package com.darf.android.uni1

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class TabFragment(private val position: Int) : Fragment() {
    private lateinit var noteListView: RecyclerView
    private lateinit var viewModel: NoteViewModel
    private lateinit var noteListAdapter: NoteListAdapter
    private val weekday by lazy { resources.getStringArray(R.array.weekdays)[position] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)
        noteListView = view.findViewById(R.id.note_list_view)
        noteListView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(view.context)
            addItemDecoration(
                DividerItemDecoration(
                    view.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            noteListAdapter = NoteListAdapter(
                this@TabFragment::onUpdateClicked,
                this@TabFragment::onDeleteClicked
            )
            adapter = noteListAdapter
            scrollToPosition(adapter!!.itemCount - 1)
        }

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(App.getInstance())
            .create(NoteViewModel::class.java)
        viewModel.getNotesById(weekday)
        viewModel.notes.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                noteListAdapter.addItems(it)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val addItemButton: Button = view.findViewById(R.id.note_list_add_item_button)
        addItemButton.setOnClickListener {
            val fragment = NewNoteFragment(this)
            fragment.show(requireActivity().supportFragmentManager, null)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addItemClick(text: String) {
        val temp = (noteListView.adapter as NoteListAdapter).getLastItem()
        val newNote = Note(
//            LocalDate.now().toString(),
            weekday,
            temp?.note_id?.plus(1) ?: 1,
            text
        )
        viewModel.saveNote(newNote)

//        val adapter = noteListView.adapter as NoteListAdapter
//        adapter.addItem(note)
//        noteListView.scrollToPosition(adapter.itemCount - 1)
    }

    fun updateItemClick(note: Note, text: String) {
        val updateNote = Note(
            note.id,
            note.note_id,
            text
        )
        viewModel.updateNote(updateNote)
    }

    private fun onUpdateClicked(note: Note) {
        val fragment = UpdateNoteFragment(this, note)
        fragment.show(requireActivity().supportFragmentManager, null)
    }

    private fun onDeleteClicked(note: Note) {
        viewModel.deleteNote(note)
    }
}