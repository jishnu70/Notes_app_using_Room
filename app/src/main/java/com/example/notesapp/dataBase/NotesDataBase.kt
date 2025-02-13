package com.example.notesapp.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 1)
abstract class NotesDataBase: RoomDatabase() {
    abstract val dao: NotesDao
}