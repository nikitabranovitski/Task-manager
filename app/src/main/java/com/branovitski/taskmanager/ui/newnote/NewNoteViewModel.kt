package com.branovitski.taskmanager.ui.newnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.branovitski.taskmanager.model.Note
import com.branovitski.taskmanager.repository.TaskManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NewNoteViewModel @Inject constructor(private val repository: TaskManagerRepository) :
    ViewModel() {

    fun updateNoteInDB(title: String, note: String) {
        viewModelScope.launch {
            try {
                repository.updateNote(Note(0, title, note, Calendar.getInstance().time.toString()))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addNewNoteToDB(title: String, note: String) {

        viewModelScope.launch {
            try {
                repository.addNote(Note(0, title, note, Calendar.getInstance().time.toString()))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}