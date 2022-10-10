package com.anonymous.notes.data

import com.anonymous.notes.model.Note

class NoteDataSource {
    fun loadNotes(): List<Note> = listOf(
        Note(
            title = "A good day",
            description = "I wish you a lovely day"
        ),
        Note(
            title = "Good luck",
            description = "I wish you good luck "
        )
    )
}