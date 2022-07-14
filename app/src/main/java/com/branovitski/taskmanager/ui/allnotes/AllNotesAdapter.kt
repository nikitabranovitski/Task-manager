package com.branovitski.taskmanager.ui.allnotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.branovitski.taskmanager.databinding.NoteListItemBinding
import com.branovitski.taskmanager.model.Notes

class AllNotesAdapter : ListAdapter<Notes, NotesViewHolder>(NotesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = NoteListItemBinding.inflate(inflater, parent, false)
        return NotesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class NotesViewHolder(private val binding: NoteListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Notes) {
        binding.titleTextView.text = item.title
        binding.noteTextView.text = item.notes
    }

}

private class NotesDiffCallback : DiffUtil.ItemCallback<Notes>() {

    override fun areItemsTheSame(oldItem: Notes, newItem: Notes) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Notes, newItem: Notes) = oldItem == newItem
}
