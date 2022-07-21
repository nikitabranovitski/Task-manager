package com.branovitski.taskmanager.ui.allnotes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.branovitski.taskmanager.databinding.NoteListItemBinding
import com.branovitski.taskmanager.model.Notes

class AllNotesAdapter(
) : ListAdapter<Notes, NotesViewHolder>(NotesDiffCallback()) {

    lateinit var onItemClick: (note: Notes, position: Int) -> Unit
    lateinit var onItemLongClick: (note: Notes, position: Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = NoteListItemBinding.inflate(inflater, parent, false)
        return NotesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onItemClick(getItem(position), position)
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClick(getItem(position), position)
            return@setOnLongClickListener true
        }
    }

}

class NotesViewHolder(private val binding: NoteListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Notes) {
        binding.titleTextView.text = item.title
        binding.noteTextView.text = item.notes
        binding.dateTextView.text = item.date
    }

}

private class NotesDiffCallback : DiffUtil.ItemCallback<Notes>() {

    override fun areItemsTheSame(oldItem: Notes, newItem: Notes) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Notes, newItem: Notes) = oldItem == newItem
}
