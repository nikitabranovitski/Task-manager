package com.branovitski.taskmanager.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.branovitski.taskmanager.model.Note
import kotlinx.coroutines.flow.*

@Dao
interface TaskManagerDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insert(notes: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("UPDATE notes SET title = :title, notes = :notes WHERE ID = :id")
    suspend fun update(id: Int, title: String, notes: String)

    @Delete
    suspend fun delete(notes: Note)
}