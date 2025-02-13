package com.example.notesapp.dataBase

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "notes_data")
data class Notes(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String? = null,
    val content: String,
    val creationDateStamp: String = getCurrentDate(),
    val creationTimeStamp: String = getCurrentTime()
) {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun getCurrentDate(): String {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return dateFormat.format(Date())
        }

        fun getCurrentTime(): String {
            val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            return timeFormat.format(Date())
        }
    }
}