package com.example.noteapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.R
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.model.Note

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.addNoteFab.setOnClickListener {
            val intent = Intent(this, NoteEditorActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        notesAdapter = NotesAdapter()
        binding.notesRecyclerView.apply {
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        // Contoh data
        val dummyNotes = listOf(
            Note(title = "Nota Pertama", content = "Ini adalah nota pertama saya."),
            Note(title = "Nota Kedua", content = "Ini adalah nota kedua saya.")
        )
        notesAdapter.submitList(dummyNotes)
    }
}
