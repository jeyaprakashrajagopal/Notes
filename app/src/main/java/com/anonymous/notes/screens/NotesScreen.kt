package com.anonymous.notes.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.anonymous.notes.R
import com.anonymous.notes.components.InputText
import com.anonymous.notes.model.Note
import com.anonymous.notes.widgets.RoundIconButton

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteScreen(notes: List<Note>, addNotes: (Note) -> Unit, removeNote: (Note) -> Unit) {
    val addNote = remember {
        mutableStateOf(false)
    }
    val noteTitle = remember {
        mutableStateOf("")
    }

    val noteContent = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    CreateAppBar(addNote) {
        ShowAddNotesDialog(addNote, noteTitle, noteContent) {
            addNotes(it)
        }
        if (notes.isEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray)
            ) {
                Text(
                    text = stringResource(id = R.string.empty_notes),
                    style = MaterialTheme.typography.h5
                )
            }
        } else {
            LazyColumn {
                items(notes) {
                    NoteRow(it) {
                        Toast.makeText(context, R.string.note_remove_success, Toast.LENGTH_LONG)
                            .show()
                        removeNote(it)
                    }
                }
            }
        }
    }
}

@Composable
fun NoteRow(note: Note, remove: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { remove() },
        elevation = 10.dp,
        backgroundColor = Color.LightGray
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Text(
                modifier = Modifier.padding(3.dp),
                text = note.title,
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(3.dp),
                text = note.description,
                style = MaterialTheme.typography.subtitle1,
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Text(
                    modifier = Modifier.padding(3.dp),
                    text = note.entryDate.toString(),
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}

@Composable
private fun ShowAddNotesDialog(
    addNote: MutableState<Boolean>,
    noteTitle: MutableState<String>,
    noteContent: MutableState<String>,
    addNoteEvent: (Note) -> Unit
) {
    val noteTitleError = remember {
        mutableStateOf(false)
    }
    val noteContentError = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    if (addNote.value) {
        AlertDialog(
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
            onDismissRequest = {
                addNote.value = false
            }, title = {
                Text(
                    text = stringResource(id = R.string.add_note),
                    style = MaterialTheme.typography.h5
                )
            }, text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    InputText(
                        modifier = Modifier.padding(5.dp),
                        text = noteTitle.value,
                        maxLine = 1,
                        imeAction = ImeAction.Next,
                        label = stringResource(id = R.string.note_title),
                        isError = noteTitleError.value,
                        onTextChange = {
                            if (it.all { char ->
                                    char.isLetter() || char.isWhitespace()
                                }) {
                                noteTitleError.value = false
                                noteTitle.value = it
                            }
                        }) {
                    }
                    InputText(
                        modifier = Modifier.padding(5.dp),
                        text = noteContent.value,
                        maxLine = 3,
                        isError = noteContentError.value,
                        label = stringResource(id = R.string.add_note),
                        onTextChange = {
                            if (it.all { char ->
                                    char.isLetter() || char.isWhitespace()
                                }) {
                                noteContentError.value = false
                                noteContent.value = it
                            }
                        }) {
                    }
                }
            }, buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = {
                        Log.d(
                            "Save note ",
                            "ShowAddNotesDialog: ${addNote.value} ${noteContent.value}"
                        )
                        if (noteTitle.value.isBlank() || noteContent.value.isBlank()) {
                            if (noteTitle.value.isBlank()) noteTitleError.value = true
                            if (noteContent.value.isBlank()) noteContentError.value = true
                        } else {
                            addNote.value = false
                            addNoteEvent(
                                Note(
                                    title = noteTitle.value,
                                    description = noteContent.value
                                )
                            )
                            noteTitle.value = ""
                            noteContent.value = ""
                            Toast.makeText(context, R.string.note_add_success, Toast.LENGTH_LONG)
                                .show()
                        }
                    }) {
                        Text(
                            text = stringResource(id = R.string.save_note),
                            color = MaterialTheme.colors.background,
                        )
                    }
                }
            })
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun CreateAppBar(addNote: MutableState<Boolean>, content: @Composable () -> Unit) {
    Scaffold(topBar = {
        CreateTopAppBar(addNote)
    }) {
        content()
    }
}

@Composable
private fun CreateTopAppBar(addNote: MutableState<Boolean>) {
    TopAppBar(
        backgroundColor = Color.LightGray
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.notes_app),
                style = MaterialTheme.typography.h5,
                color = Color.Black,
                modifier = Modifier.padding(3.dp)
            )
            RoundIconButton(
                imageVector = Icons.Filled.Add,
                contentDescription = R.string.add_note,
                imageTint = Color.Black
            ) {
                addNote.value = true
            }
        }
    }
}
