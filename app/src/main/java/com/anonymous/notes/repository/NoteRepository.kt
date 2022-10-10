package com.anonymous.notes.repository

import com.anonymous.notes.data.NoteDatabaseDao
import com.anonymous.notes.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDatabaseDao: NoteDatabaseDao) {

    suspend fun addNote(note: Note) = noteDatabaseDao.insertNote(note)
    suspend fun getNoteById(id: String) = noteDatabaseDao.getNoteById(id)
    suspend fun updateNote(note: Note) = noteDatabaseDao.update(note)
    suspend fun deleteAllNotes() = noteDatabaseDao.deleteAll()
    suspend fun deleteNote(note: Note) = noteDatabaseDao.deleteNote(note)
    suspend fun deleteNoteById(id: String) = noteDatabaseDao.deleteNoteById(id)
    fun getNotes(): Flow<List<Note>> =
        noteDatabaseDao.getNotes().flowOn(Dispatchers.IO).conflate()
}