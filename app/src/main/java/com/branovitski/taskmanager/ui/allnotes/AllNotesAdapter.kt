package com.branovitski.taskmanager.ui.allnotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.branovitski.taskmanager.databinding.NoteListItemBinding
import com.branovitski.taskmanager.model.Note

class AllNotesAdapter : ListAdapter<Note, NotesViewHolder>(NotesDiffCallback()) {

    lateinit var onItemClick: (note: Note) -> Unit
    lateinit var onItemLongClick: (note: Note, position: Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = NoteListItemBinding.inflate(inflater, parent, false)
        return NotesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onItemClick(getItem(position))
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClick(getItem(position), position)
            return@setOnLongClickListener true
        }
    }

}

class NotesViewHolder(private val binding: NoteListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Note) {
        binding.titleTextView.text = item.title
        binding.noteTextView.text = item.notes
        binding.dateTextView.text = item.date
    }
}

private class NotesDiffCallback : DiffUtil.ItemCallback<Note>() {

    override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem
}
