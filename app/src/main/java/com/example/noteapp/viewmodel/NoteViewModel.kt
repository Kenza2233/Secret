package com.example.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.noteapp.model.*
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao: NoteDao
    val allNotes: LiveData<List<NoteWithImages>>

    init {
        val database = AppDatabase.getDatabase(application)
        noteDao = database.noteDao()
        allNotes = noteDao.getAllNotesWithImages().asLiveData()
    }

    fun insertNote(note: Note, images: List<Image>) = viewModelScope.launch {
        val noteId = noteDao.insertNote(note)
        images.forEachIndexed { index, image ->
            val imageId = noteDao.insertImage(image)
            noteDao.insertNoteImageCrossRef(NoteImageCrossRef(noteId.toInt(), imageId.toInt(), index))
        }
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteDao.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteDao.deleteNote(note)
    }

    fun getNoteWithImages(noteId: Int): LiveData<NoteWithImages> {
        return noteDao.getNoteWithImages(noteId).asLiveData()
    }
}
