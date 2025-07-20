package com.example.noteapp.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: Image): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteImageCrossRef(crossRef: NoteImageCrossRef)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Transaction
    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteWithImages(noteId: Int): Flow<NoteWithImages>

    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
    fun getAllNotes(): Flow<List<Note>>
}
