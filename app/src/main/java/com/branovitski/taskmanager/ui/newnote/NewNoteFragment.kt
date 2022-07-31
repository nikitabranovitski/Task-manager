package com.branovitski.taskmanager.ui.newnote

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.branovitski.taskmanager.R
import com.branovitski.taskmanager.databinding.FragmentNewNoteBinding
import com.branovitski.taskmanager.model.Note
import com.branovitski.taskmanager.ui.allnotes.AllNotesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewNoteFragment : Fragment() {

    private lateinit var binding: FragmentNewNoteBinding

    private val viewModel by viewModels<NewNoteViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.noteData.observe(viewLifecycleOwner) { note ->
            if (note != null) {
                binding.titleEditText.setText(note.title)
                binding.noteEditText.setText(note.notes)
            }
        }

        binding.createNewNodeButton.setOnClickListener {
            viewModel.createOrUpdateNote(
                binding.titleEditText.text.toString().trim(),
                binding.noteEditText.text.toString().trim()
            )
        }
    }

    companion object {

        const val ARG_NOTE = "note"

        fun newInstance(note: Note? = null): NewNoteFragment {
            return NewNoteFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_NOTE, note)
                }
            }
        }
    }

}