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
        getNoteFromList()
        binding.createNewNodeButton.setOnClickListener {
            if (arguments != null) {
                viewModel.updateNoteInDB(
                    binding.titleEditText.text.toString(),
                    binding.noteEditText.text.toString()
                )
            } else {
                viewModel.addNewNoteToDB(
                    binding.titleEditText.text.toString(),
                    binding.noteEditText.text.toString()
                )
            }
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, AllNotesFragment())
                .commit()
        }
    }

    private fun getNoteFromList() {
        val bundle = arguments
        if (bundle != null) {
            binding.titleEditText.setText(bundle.getString("title"))
            binding.noteEditText.setText(bundle.getString("note"))
        }
    }

}