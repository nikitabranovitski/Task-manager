package com.branovitski.taskmanager.repository

import com.branovitski.taskmanager.database.TaskManagerDAO
import com.branovitski.taskmanager.model.Notes
import javax.inject.Inject

class TaskManagerRepository @Inject constructor(private val notesDao: TaskManagerDAO) {

    suspend fun addNote(note: Notes) = notesDao.insert(note)
    suspend fun deleteNote(note: Notes) = notesDao.delete(note)

    suspend fun updateNote(note: Notes) = notesDao.update(note.id, note.title, note.notes)
    suspend fun getAllNotes() = notesDao.getAllNotes()
}