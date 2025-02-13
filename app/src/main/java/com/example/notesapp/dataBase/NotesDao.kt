package com.example.notesapp.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Upsert
    suspend fun insertNote(note: Notes)

    @Query("SELECT * FROM notes_data ORDER BY creationDateStamp DESC, creationTimeStamp DESC")
    fun getAllNotes(): Flow<List<Notes>>

    @Delete
    suspend fun deleteNotes(notes: List<Notes>)
}