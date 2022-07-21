package com.branovitski.taskmanager.ui.allnotes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.branovitski.taskmanager.model.Notes
import com.branovitski.taskmanager.repository.TaskManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllNotesViewModel @Inject constructor(private val repository: TaskManagerRepository) :
    ViewModel() {

    val notesData = MutableLiveData<List<Notes>>()

    init {
        getNotesListFromDB()
    }

    fun getNotesListFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                notesData.postValue(repository.getAllNotes())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteNoteFromDB(note: Notes) {
        viewModelScope.launch {
            try {
                repository.deleteNote(note)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun filterListOfNotes(newText: String?): ArrayList<Notes> {
        val filteredList = arrayListOf<Notes>()
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