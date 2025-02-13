package com.example.notesapp.dataBase

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {
    private val _notes = MutableStateFlow<NoteUIState>(NoteUIState.Loading)
    val notes = _notes.asStateFlow()

    var title by mutableStateOf("")
    var content by mutableStateOf("")

    private val noteToBeEdited = mutableStateOf<Notes>(Notes(content = ""))

    init {
        getAllNotes()
    }

    fun updateNote(editedTitle: String?, editedContent: String) {
        viewModelScope.launch {
            val note = noteToBeEdited.value.copy(title = editedTitle, content = editedContent)
            repository.updateNote(note = note)
            updateTitle(newTitle = "")
            updateContent(newContent = "")
        }
    }

    fun editingNote(noteEdit: Notes) {
        _notes.value = NoteUIState.IsEditing
        updateTitle(newTitle = noteEdit.title)
        updateContent(newContent = noteEdit.content)
        noteToBeEdited.value = noteEdit
    }

    fun getAllNotes() {
        viewModelScope.launch {
            _notes.value = NoteUIState.Loading
            repository.getAllNotes().collectLatest { notes ->
                _notes.value = if (notes.isEmpty()) {
                    NoteUIState.Error("Empty")
                } else {
                    NoteUIState.Success(notes = notes)
                }
            }
        }
    }

    fun updateTitle(newTitle: String? = null) {
        title = newTitle ?: ""
    }

    fun updateContent(newContent: String) {
        content = newContent
    }

    fun addNotes(title: String? = null, content: String) {
        viewModelScope.launch {
            if (content.isNotEmpty()) {
                val note = Notes(title = title, content = content)
                repository.createNotes(note = note)
            }
            updateTitle(newTitle = "")
            updateContent(newContent = "")
        }
    }

    fun deleteNotes(notes: List<Notes>) {
        viewModelScope.launch {
            repository.deleteNote(notes = notes)
        }
    }
}