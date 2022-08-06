package com.branovitski.taskmanager.ui.allnotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.branovitski.taskmanager.databinding.NoteListItemBinding
import com.branovitski.taskmanager.model.Note
import io.karn.notify.entities.Payload
import kotlin.collections.List as List

private const val ARG_TITLE = "arg.title"
private const val ARG_NOTE = "arg.note"

class AllNotesAdapter : ListAdapter<Note, AllNotesAdapter.NotesViewHolder>(NotesDiffCallback()) {

    lateinit var onItemClick: (note: Note) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = NoteListItemBinding.inflate(inflater, parent, false)
        return NotesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        onBindViewHolder(holder, position, emptyList())
    }

    override fun onBindViewHolder(
        holder: NotesViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (payloads.isEmpty()) {
            holder.bind(getItem(position))
        } else {
            val bundle = payloads[0] as Bundle
            holder.update(bundle)
        }
    }


    inner class NotesViewHolder(private val binding: NoteListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onItemClick(getItem(adapterPosition))
            }
        }

        fun update(bundle: Bundle) {
            if (bundle.containsKey(ARG_NOTE)) {
                binding.noteTextView.text = bundle.getString(ARG_NOTE)
            }
            if (bundle.containsKey(ARG_TITLE)) {
                binding.titleTextView.text = bundle.getString(ARG_TITLE)
            }
        }

        fun bind(item: Note) {
            binding.titleTextView.text = item.title
            binding.noteTextView.text = item.notes
            binding.dateTextView.text = item.date
        }
    }

}

private class NotesDiffCallback : DiffUtil.ItemCallback<Note>() {

    override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem

    override fun getChangePayload(oldItem: Note, newItem: Note): Any {
        val diff = Bundle()
        if (oldItem.title != newItem.title) {
            diff.putString(ARG_TITLE, newItem.title)
        }
        if (oldItem.notes != newItem.notes) {
            diff.putString(ARG_NOTE, newItem.notes)
        }
        return diff
    }
}

abstract class SwipeToDeleteCallBack :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
       return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        TODO("Not yet implemented")
    }

}


