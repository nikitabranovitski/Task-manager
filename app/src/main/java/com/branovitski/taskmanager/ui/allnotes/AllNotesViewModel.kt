package com.branovitski.taskmanager.ui.allnotes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.branovitski.taskmanager.Screens
import com.branovitski.taskmanager.model.Note
import com.branovitski.taskmanager.repository.TaskManagerRepository
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllNotesViewModel @Inject constructor(
    private val repository: TaskManagerRepository,
    private val router: Router
) :
    ViewModel() {

    val notesData = MutableLiveData<List<Note>>()
    lateinit var showToast: () -> Unit
    lateinit var showErrorToast: () -> Unit

    init {
        getNotesListFromDB()
    }

    private fun getNotesListFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getAllNotes().collect {
                    notesData.postValue(it)
                }
            } catch (e: Exception) {
                showErrorToast()
            }
        }
    }

    fun onDeleteNoteMenuClicked(note: Note) {
        viewModelScope.launch {
            try {
                repository.deleteNote(note)
                showToast()
            } catch (e: Exception) {
                showErrorToast()
            }
        }
    }

    fun setFilteredListOfNotes(newText: String?): ArrayList<Note> {
        val filteredList = arrayListOf<Note>()
        notesData.value?.forEach { note ->
            if ((note.title.lowercase()
                    .contains(newText!!.lowercase())) || (note.notes.lowercase()
                    .contains(
                        newText.lowercase()
                    ))
            ) {
                filteredList.add(note)
            }
        }
        return filteredList
    }

    fun onOpenNewNoteScreen(note: Note? = null) {
        router.navigateTo(Screens.NewNote(note))
    }
}