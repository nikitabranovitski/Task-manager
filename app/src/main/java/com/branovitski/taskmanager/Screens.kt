package com.branovitski.taskmanager

import android.content.Intent
import com.branovitski.taskmanager.model.Note
import com.branovitski.taskmanager.ui.allnotes.AllNotesFragment
import com.branovitski.taskmanager.ui.newnote.NewNoteFragment
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun AllNotes() = FragmentScreen { AllNotesFragment() }
    fun NewNote(note: Note? = null) = FragmentScreen { NewNoteFragment.newInstance(note) }
}