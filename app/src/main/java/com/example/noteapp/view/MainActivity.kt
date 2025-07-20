package com.example.noteapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notesAdapter: NotesAdapter
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        noteViewModel.allNotes.observe(this) { notes ->
            notesAdapter.submitList(notes)
        }

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
    }
}
