package com.example.noteapp.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.speech.RecognizerIntent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.noteapp.databinding.ActivityNoteEditorBinding
import com.example.noteapp.model.Image
import com.example.noteapp.model.Note
import com.example.noteapp.viewmodel.NoteViewModel
import java.io.File

class NoteEditorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteEditorBinding
    private val noteViewModel: NoteViewModel by viewModels()
    private var currentNote: Note? = null
    private val selectedImages = mutableListOf<Image>()

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val VIDEO_PICK_CODE = 1001
        private const val SPEECH_REQUEST_CODE = 1002
        private const val BACKGROUND_IMAGE_PICK_CODE = 1003
        private const val RECORD_AUDIO_PERMISSION_CODE = 101
        const val EXTRA_NOTE_ID = "extra_note_id"

        fun newIntent(context: Context, noteId: Int? = null): Intent {
            return Intent(context, NoteEditorActivity::class.java).apply {
                noteId?.let { putExtra(EXTRA_NOTE_ID, it) }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getIntExtra(EXTRA_NOTE_ID, -1).takeIf { it != -1 }?.let { noteId ->
            noteViewModel.getNoteWithImages(noteId).observe(this) { noteWithImages ->
                currentNote = noteWithImages.note
                binding.titleEditText.setText(noteWithImages.note.title)
                binding.contentEditText.setText(noteWithImages.note.content)
                noteWithImages.note.backgroundImageUri?.also { setBackgroundImage(Uri.parse(it)) }
                // Paparkan imej sedia ada...
            }
        }

        binding.changeColorButton.setOnClickListener {
            showColorPickerDialog()
        }

        binding.addImageButton.setOnClickListener {
            openImagePicker()
        }

        binding.convertToGifButton.setOnClickListener {
            openVideoPicker()
        }

        binding.voiceToTextButton.setOnClickListener {
            startVoiceRecognition()
        }

        binding.setBackgroundImageButton.setOnClickListener {
            openBackgroundImagePicker()
        }
    }

    private fun openFilePicker(type: String, requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "$type/*"
        startActivityForResult(intent, requestCode)
    }

    private fun openBackgroundImagePicker() {
        openFilePicker("image", BACKGROUND_IMAGE_PICK_CODE)
    }

    private fun setBackgroundImage(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    binding.root.background = resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })
        currentNote = currentNote?.copy(backgroundImageUri = uri.toString())
            ?: Note(title = "", content = "", backgroundImageUri = uri.toString())
    }

    private fun startVoiceRecognition() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), RECORD_AUDIO_PERMISSION_CODE)
        } else {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Cakap sekarang...")
            startActivityForResult(intent, SPEECH_REQUEST_CODE)
        }
    }

    private fun openVideoPicker() {
        openFilePicker("video", VIDEO_PICK_CODE)
    }

    private fun convertVideoToGif(videoUri: Uri) {
        val outputDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val outputFile = File(outputDir, "output_${System.currentTimeMillis()}.gif")

        val command = "-i ${videoUri.path} -vf \"fps=10,scale=320:-1:flags=lanczos\" -c:v gif ${outputFile.absolutePath}"

        FFmpeg.executeAsync(command) { _, returnCode ->
            if (returnCode == Config.RETURN_CODE_SUCCESS) {
                // Penukaran berjaya, tambah GIF pada nota
                selectedImages.add(Image(uri = Uri.fromFile(outputFile).toString(), caption = "GIF"))
                // Paparkan GIF yang baru dibuat...
            } else {
                // Penukaran gagal
            }
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
        openFilePicker("image", IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IMAGE_PICK_CODE -> {
                    data?.data?.let { uri ->
                        selectedImages.add(Image(uri = uri.toString(), caption = null))
                        // Paparkan imej yang baru dipilih...
                    }
                }
                VIDEO_PICK_CODE -> {
                    data?.data?.let { uri ->
                        convertVideoToGif(uri)
                    }
                }
                SPEECH_REQUEST_CODE -> {
                    data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.let { results ->
                        val spokenText = results[0]
                        binding.contentEditText.append(" $spokenText")
                    }
                }
                BACKGROUND_IMAGE_PICK_CODE -> {
                    data?.data?.let { uri ->
                        setBackgroundImage(uri)
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RECORD_AUDIO_PERMISSION_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startVoiceRecognition()
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
