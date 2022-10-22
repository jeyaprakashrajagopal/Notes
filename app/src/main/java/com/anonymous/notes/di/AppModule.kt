package com.anonymous.notes.di

import android.content.Context
import androidx.room.Room
import com.anonymous.notes.data.NoteDatabase
import com.anonymous.notes.data.NoteDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideNotesDao(noteDatabase: NoteDatabase): NoteDatabaseDao = noteDatabase.noteDao()

    @Singleton
    @Provides
    fun provideNoteAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, NoteDatabase::class.java, "notes_db")
            .fallbackToDestructiveMigration()
            .build()
}

