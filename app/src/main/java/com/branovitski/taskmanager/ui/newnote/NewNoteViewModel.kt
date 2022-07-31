package com.branovitski.taskmanager.ui.newnote

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.branovitski.taskmanager.Screens
import com.branovitski.taskmanager.TaskManagerActivity
import com.branovitski.taskmanager.TaskManagerApp
import com.branovitski.taskmanager.model.Note
import com.branovitski.taskmanager.repository.TaskManagerRepository
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NewNoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TaskManagerRepository,
    private var router: Router

) :
    ViewModel() {

    private val sourceNote: Note? = savedStateHandle[NewNoteFragment.ARG_NOTE]

    val noteData: MutableLiveData<Note> = MutableLiveData<Note>().apply {
        value = sourceNote
    }

    fun createOrUpdateNote(title: String, note: String) {
        viewModelScope.launch {
            try {
                val newNote = sourceNote?.copy(title = title, notes = note)
                    ?: Note(
                        title = title,
                        notes = note,
                        date = getCurrentDate()
                    )
                repository.addNote(newNote)
                router.exit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy hh:mm aa")
        return sdf.format(Date())
    }
}