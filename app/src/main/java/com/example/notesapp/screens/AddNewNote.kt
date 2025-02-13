package com.example.notesapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.notesapp.dataBase.NoteUIState
import com.example.notesapp.dataBase.NotesViewModel
import com.example.notesapp.screenRoutes.ScreenRoutes

@Composable
fun AddNewNote(
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel,
    navController: NavController
) {
    val title = viewModel.title
    val content = viewModel.content

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TextField(
            value = title, onValueChange = { viewModel.updateTitle(it) },
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
                .align(Alignment.Start),
            colors = TextFieldDefaults.colors()
                .copy(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.White,
                    focusedLabelColor = Color.White
                ),
            placeholder = {
                Text(
                    text = "TITLE",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            maxLines = 1
        )
        TextField(
            value = content,
            onValueChange = { viewModel.updateContent(it) },
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth()
                .align(Alignment.Start),
            colors = TextFieldDefaults.colors()
                .copy(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.White
                ),
            placeholder = {
                Text(
                    text = "Content",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        )

        Button(
            onClick = {
                if (content.isNotEmpty()) {
                    when (viewModel.notes.value) {
                        is NoteUIState.IsEditing -> {
                            viewModel.updateNote(editedTitle = title, editedContent = content)
                        }

                        else -> {
                            when {
                                title.isEmpty() == true -> {
                                    viewModel.addNotes(content = content)
                                }

                                title.isNotEmpty() == true -> {
                                    viewModel.addNotes(title = title, content = content)
                                }
                            }
                        }
                    }

                    navController.navigate(ScreenRoutes.mainScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {

                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) { Text(text = "Submit") }
    }
}