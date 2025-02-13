package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.notesapp.dataBase.NotesDataBase
import com.example.notesapp.dataBase.NotesRepository
import com.example.notesapp.dataBase.NotesViewModel
import com.example.notesapp.dataBase.NotesViewModelFactory
import com.example.notesapp.ui.theme.NotesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataBase = Room.databaseBuilder(
            applicationContext,
            NotesDataBase::class.java,
            "notes_database"
        ).build()

        val dao = dataBase.dao
        val repository = NotesRepository(notesDao = dao)
        val viewModelFactory = NotesViewModelFactory(repository = repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[NotesViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NotesAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationHandler(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}