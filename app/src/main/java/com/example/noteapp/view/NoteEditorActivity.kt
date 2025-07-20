package com.example.noteapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.viewModels
import com.example.noteapp.databinding.ActivityNoteEditorBinding
import com.example.noteapp.model.Note
import com.example.noteapp.model.Image
import com.example.noteapp.viewmodel.NoteViewModel

class NoteEditorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteEditorBinding
    private val noteViewModel: NoteViewModel by viewModels()
    private var currentNote: Note? = null
    private val selectedImages = mutableListOf<Image>()

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        const val EXTRA_NOTE_ID = "extra_note_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getIntExtra(EXTRA_NOTE_ID, -1)
        if (noteId != -1) {
            noteViewModel.getNoteWithImages(noteId).observe(this) { noteWithImages ->
                currentNote = noteWithImages.note
                binding.titleEditText.setText(noteWithImages.note.title)
                binding.contentEditText.setText(noteWithImages.note.content)
                // Paparkan imej sedia ada...
            }
        }

        binding.changeColorButton.setOnClickListener {
            showColorPickerDialog()
        }

        binding.addImageButton.setOnClickListener {
            openImagePicker()
        }
    }

    private fun saveNote() {
        val title = binding.titleEditText.text.toString()
        val content = binding.contentEditText.text.toString()

        if (title.isNotEmpty() || content.isNotEmpty()) {
            val note = currentNote?.copy(title = title, content = content, updatedAt = System.currentTimeMillis())
                ?: Note(title = title, content = content)

            noteViewModel.insertNote(note, selectedImages)
        }
        finish()
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            data?.data?.let { uri ->
                selectedImages.add(Image(uri = uri.toString(), caption = null))
                // Paparkan imej yang baru dipilih...
            }
        }
    }

    private fun showColorPickerDialog() {
        val colorPickerDialog = ColorPickerDialogFragment { color ->
            currentNote = currentNote?.copy(backgroundColor = color)
                ?: Note(title = "", content = "", backgroundColor = color)
            binding.root.setBackgroundColor(android.graphics.Color.parseColor(color))
        }
        colorPickerDialog.show(supportFragmentManager, "ColorPickerDialog")
    }

    override fun onSupportNavigateUp(): Boolean {
        saveNote()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        saveNote()
        super.onBackPressed()
    }
}
