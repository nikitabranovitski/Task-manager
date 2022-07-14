package com.branovitski.taskmanager.repository

import com.branovitski.taskmanager.database.TaskManagerDAO
import com.branovitski.taskmanager.model.Notes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskManagerRepository @Inject constructor(private val notes: TaskManagerDAO) {

    suspend fun addNote(note: Notes) = notes.insert(note)
    suspend fun deleteNote(note: Notes) = notes.delete(note)

    suspend fun updateNote(note: Notes) = notes.update(note.id, note.title, note.notes)
    suspend fun getAllNotes() = notes.getAllNotes()
}