package com.example.notesapp.dataBase

import kotlinx.coroutines.flow.Flow

class NotesRepository(private val notesDao: NotesDao) {
    fun getAllNotes(): Flow<List<Notes>> {
        return notesDao.getAllNotes()
    }

    suspend fun createNotes(note: Notes) {
        notesDao.insertNote(note = note)
    }

    suspend fun deleteNote(notes: List<Notes>) {
        notesDao.deleteNotes(notes = notes)
    }

    suspend fun updateNote(note: Notes) {
        notesDao.insertNote(note = note)
    }
}