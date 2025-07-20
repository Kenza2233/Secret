package com.example.noteapp.model

import androidx.room.Entity

@Entity(tableName = "note_images", primaryKeys = ["note_id", "image_id"])
data class NoteImageCrossRef(
    val note_id: Int,
    val image_id: Int,
    val position: Int
)
