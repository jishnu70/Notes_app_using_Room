package com.example.notesapp.screenRoutes

sealed class ScreenRoutes(val route: String) {
    object mainScreen: ScreenRoutes(route = "main_screen")
    object noteScreen: ScreenRoutes(route = "note_screen")
}