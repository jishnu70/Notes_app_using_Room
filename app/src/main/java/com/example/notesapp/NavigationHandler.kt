package com.example.notesapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notesapp.dataBase.NotesViewModel
import com.example.notesapp.screenRoutes.ScreenRoutes
import com.example.notesapp.screens.AddNewNote
import com.example.notesapp.screens.AllNotes

@Composable
fun NavigationHandler(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel
) {

    NavHost(navController = navController, startDestination = ScreenRoutes.mainScreen.route) {
        composable(route = ScreenRoutes.mainScreen.route) {
            AllNotes(modifier = modifier, viewModel = viewModel, navController = navController)
        }

        composable(route = ScreenRoutes.noteScreen.route) {
            AddNewNote(modifier = modifier, viewModel = viewModel, navController = navController)
        }
    }
}