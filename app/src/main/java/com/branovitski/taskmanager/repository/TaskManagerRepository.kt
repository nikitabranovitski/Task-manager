package com.branovitski.taskmanager.repository

import com.branovitski.taskmanager.database.TaskManagerDAO
import com.branovitski.taskmanager.model.Note
import javax.inject.Inject

class TaskManagerRepository @Inject constructor(private val notesDao: TaskManagerDAO) {

    suspend fun addNote(note: Note) = notesDao.insert(note)

    suspend fun deleteNote(note: Note) = notesDao.delete(note)

    suspend fun updateNote(note: Note) = notesDao.update(note.id, note.title, note.notes)

    suspend fun getAllNotes() = notesDao.getAllNotes()
}