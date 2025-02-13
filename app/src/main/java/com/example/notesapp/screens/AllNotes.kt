package com.example.notesapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.notesapp.dataBase.NoteUIState
import com.example.notesapp.dataBase.Notes
import com.example.notesapp.dataBase.NotesViewModel
import com.example.notesapp.screenRoutes.ScreenRoutes

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AllNotes(
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel,
    navController: NavController
) {
    val color = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        when (val notes = viewModel.notes.collectAsState().value) {
            NoteUIState.Loading -> {}
            is NoteUIState.Success -> {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f)
                        .padding(8.dp)
                ) {
                    notes.notes.forEach { note ->
                        NoteBoxCard(
                            note = note,
                            onEditing = {
                                viewModel.editingNote(it)
                                navController.navigate(ScreenRoutes.noteScreen.route)
                            },
                            onDeleteButtonClicked = { viewModel.deleteNotes(listOf(it)) }
                        )
                    }
                }
                CreationBox(
                    modifier = Modifier.align(Alignment.End),
                    navController = navController,
                    color = color
                )
            }

            is NoteUIState.IsEditing -> {}
            is NoteUIState.Error -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .weight(3f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = notes.error,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.displayMedium,
                        color = color,
                        textAlign = TextAlign.Center
                    )
                }
                CreationBox(
                    modifier = Modifier.align(Alignment.End),
                    navController = navController,
                    color = color
                )
            }
        }
    }
}

@Composable
fun CreationBox(modifier: Modifier, navController: NavController, color: Color) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .padding(8.dp)
    ) {
        IconButton(
            onClick = {
                navController.navigate(ScreenRoutes.noteScreen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier
                .shadow(
                    elevation = 32.dp,
                    shape = CircleShape,
                    clip = true,
                    spotColor = color,
                    ambientColor = color
                )
                .background(Color.DarkGray, CircleShape)
                .size(72.dp)
                .padding(6.dp)
        ) {
            Icon(
                Icons.Default.Create,
                contentDescription = "Create Note",
                tint = color,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun NoteBoxCard(note: Notes, onEditing: (Notes) -> Unit, onDeleteButtonClicked: (Notes) -> Unit) {
    Box(
        modifier = Modifier
            .padding(6.dp)
            .size(width = 140.dp, height = 250.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.DarkGray)
                    .padding(4.dp)
                    .clickable {
                        onEditing(note)
                    }
            ) {
                Text(
                    text = note.content,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier
                        .matchParentSize()
                        .align(Alignment.TopStart)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Created: ${note.creationDateStamp}",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .weight(3f)
                        .padding(4.dp),
                    color = Color.LightGray,
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                )
                IconButton(
                    onClick = { onDeleteButtonClicked(note) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete Note",
                        tint = Color.White
                    )
                }
            }
        }
    }
}