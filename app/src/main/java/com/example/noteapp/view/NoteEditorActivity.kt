package com.example.noteapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.noteapp.R
import com.example.noteapp.databinding.ActivityNoteEditorBinding

class NoteEditorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteEditorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
