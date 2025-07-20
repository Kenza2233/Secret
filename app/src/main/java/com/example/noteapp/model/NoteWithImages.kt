package com.example.noteapp.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class NoteWithImages(
    @Embedded val note: Note,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = NoteImageCrossRef::class,
            parentColumn = "note_id",
            entityColumn = "image_id"
        )
    )
    val images: List<Image>
)
