package com.branovitski.taskmanager.ui.allnotes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.branovitski.taskmanager.model.Note
import com.branovitski.taskmanager.repository.TaskManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllNotesViewModel @Inject constructor(private val repository: TaskManagerRepository) :
    ViewModel() {

    val notesData = MutableLiveData<List<Note>>()

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
                e.printStackTrace()
            }
        }
    }

    fun onDeleteNoteMenuClicked(note: Note) {
        viewModelScope.launch {
            try {
                repository.deleteNote(note)
            } catch (e: Exception) {
                e.printStackTrace()
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
}