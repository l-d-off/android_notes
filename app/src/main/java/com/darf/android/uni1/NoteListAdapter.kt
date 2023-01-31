package com.darf.android.uni1

import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteListAdapter(
    private val onUpdateClicked: (note: Note) -> Unit,
    private val onDeleteClicked: (note: Note) -> Unit
) :
    RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    private var noteList = mutableListOf<Note>()

    inner class ViewHolder(
        itemView: View,
        private val onUpdateClicked: (note: Note) -> Unit,
        private val onDeleteClicked: (note: Note) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {
        private var note: Note? = null
        private val itemTextView: TextView = itemView.findViewById(R.id.item_text)

        fun bind(data: Note) {
            note = data
            itemTextView.text = data.note_text
            itemView.setOnClickListener {
                val popup = PopupMenu(itemView.context, itemView)
                popup.setOnMenuItemClickListener(onEditMenu)
                popup.inflate(R.menu.menu_note_item)
                popup.gravity = Gravity.END
                popup.show()
            }
        }

//        init {
//            itemView.setOnClickListener(this)
//        }
//
//        override fun onClick(v: View?) {
////            val position = adapterPosition
//            onUpdateClicked(note!!)
//        }

//        override fun onCreateContextMenu(
//            menu: ContextMenu,
//            v: View?,
//            menuInfo: ContextMenu.ContextMenuInfo?
//        ) {
//            val edit: MenuItem = menu.add(Menu.NONE, 1, 1, "Edit")
//            val delete: MenuItem = menu.add(Menu.NONE, 2, 2, "Delete")
//            edit.setOnMenuItemClickListener(onEditMenu)
//            delete.setOnMenuItemClickListener(onEditMenu)
//        }

        private val onEditMenu: PopupMenu.OnMenuItemClickListener =
            PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.edit_item -> onUpdateClicked(note!!)
                    R.id.delete_item -> onDeleteClicked(note!!)
                }
                return@OnMenuItemClickListener true
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_list_item, parent, false)
        return ViewHolder(
            itemView,
            onUpdateClicked,
            onDeleteClicked
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(noteList[position])
    }

    override fun getItemCount(): Int = noteList.size

    fun addItem(note: Note) {
        noteList.add(note)
        notifyItemInserted(itemCount - 1)
    }

    fun addItems(list: List<Note>) {
        noteList.clear()
        noteList.addAll(list)
        notifyDataSetChanged()
    }

    fun getLastItem(): Note? {
        return if (itemCount == 0) null else noteList[itemCount - 1]
    }

    fun removeAt(position: Int): Note {
        val note = noteList[position]
        noteList.removeAt(position)
        notifyItemRemoved(position)
        return note
    }
}