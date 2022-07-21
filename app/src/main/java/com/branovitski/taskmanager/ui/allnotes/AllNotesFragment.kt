package com.branovitski.taskmanager.ui.allnotes

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.branovitski.taskmanager.R
import com.branovitski.taskmanager.databinding.FragmentAllNotesBinding
import com.branovitski.taskmanager.model.Notes
import com.branovitski.taskmanager.ui.newnote.NewNoteFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AllNotesFragment : Fragment() {

    private lateinit var binding: FragmentAllNotesBinding

    private val viewModel by viewModels<AllNotesViewModel>()

    private var notesAdapter: AllNotesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllNotesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNotesList()

        binding.addNewNoteButton.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, NewNoteFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                notesAdapter?.submitList(viewModel.filterListOfNotes(newText))
                return true
            }

        })
    }

    @SuppressLint("ResourceType", "NotifyDataSetChanged")
    private fun setupNotesList() {
        notesAdapter = AllNotesAdapter().apply {
            onItemLongClick = { note, position ->
                val popupMenu = PopupMenu(requireContext(), binding.notesList[position])
                popupMenu.menuInflater.inflate(R.menu.note_option, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.delete_note -> {
                            viewModel.deleteNoteFromDB(note)
                            viewModel.getNotesListFromDB()
                            true
                        }
                        R.id.edit_note -> {
                            true
                        }
                        else -> false
                    }
                }
                popupMenu.show()
            }

            onItemClick = { note, position ->

            }
        }
        binding.notesList.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.notesList.adapter = notesAdapter

        viewModel.notesData.observe(viewLifecycleOwner) {
            notesAdapter?.submitList(it)
        }
    }

}