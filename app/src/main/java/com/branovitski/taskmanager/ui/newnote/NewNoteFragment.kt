package com.branovitski.taskmanager.ui.newnote

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.branovitski.taskmanager.R
import com.branovitski.taskmanager.databinding.FragmentNewNoteBinding
import com.branovitski.taskmanager.ui.allnotes.AllNotesFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

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
        binding.createNewNodeButton.setOnClickListener {
            viewModel.addNewNoteToDB(
                binding.titleEditText.text.toString(),
                binding.noteEditText.text.toString()
            )
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, AllNotesFragment())
                .commit()
        }
    }

}