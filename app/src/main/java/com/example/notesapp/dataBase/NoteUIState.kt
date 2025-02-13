package com.example.notesapp.dataBase

sealed class NoteUIState {
    object Loading: NoteUIState()
    data class Success(val notes: List<Notes>): NoteUIState()
    object IsEditing: NoteUIState()
    data class Error(val error: String): NoteUIState()
}