package com.branovitski.taskmanager.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.branovitski.taskmanager.model.Notes

@Dao
interface TaskManagerDAO {

    @Insert(onConflict = REPLACE)
    fun insert(notes: Notes)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): List<Notes>

    @Query("UPDATE notes SET title = :title, notes = :notes WHERE ID = :id")
    fun update(id: Int, title: String, notes: String)

    @Delete
    fun delete(notes: Notes)
}