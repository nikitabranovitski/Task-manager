package com.branovitski.taskmanager.ui.allnotes

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.branovitski.taskmanager.R
import com.branovitski.taskmanager.TaskManagerApp
import com.branovitski.taskmanager.databinding.FragmentAllNotesBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            showToast = {
                makeToast("This note has been successfully deleted!")
            }
            showErrorToast = {
                makeToast("Error")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNotesList()

        binding.addNewNoteButton.setOnClickListener {
            viewModel.onOpenNewNoteScreen()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                notesAdapter?.submitList(viewModel.setFilteredListOfNotes(newText))
                return true
            }
        })
    }

    @SuppressLint("ResourceType", "NotifyDataSetChanged")
    private fun setupNotesList() {
        val swipeGesture = object : SwipeToDeleteCallBack() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.onDeleteNoteMenuClicked(viewModel.notesData.value!![viewHolder.adapterPosition])
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(binding.notesList)

        notesAdapter = AllNotesAdapter().apply {
            onItemClick = {
                viewModel.onOpenNewNoteScreen(it)
            }
        }
        binding.notesList.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.notesList.adapter = notesAdapter

        viewModel.notesData.observe(viewLifecycleOwner) {
            notesAdapter?.submitList(it)
        }
    }

    private fun makeToast(text: String) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_LONG
        ).show()
    }

}