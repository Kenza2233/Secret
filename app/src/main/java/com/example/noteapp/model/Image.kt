package com.example.noteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class Image(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uri: String,
    val caption: String?,
    val createdAt: Long = System.currentTimeMillis()
)
