package com.example.noteapp.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.ItemNoteBinding
import com.example.noteapp.model.NoteWithImages
import com.example.noteapp.util.hide
import com.example.noteapp.util.loadImage
import com.example.noteapp.util.show

class NotesAdapter : ListAdapter<NoteWithImages, NotesAdapter.NoteViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = getItem(position)
        holder.bind(currentNote)
    }

    class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(noteWithImages: NoteWithImages) {
            binding.apply {
                noteTitleTextView.text = noteWithImages.note.title
                noteContentTextView.text = noteWithImages.note.content

                noteWithImages.images.firstOrNull()?.let {
                    noteImageView.show()
                    noteImageView.loadImage(it.uri)
                } ?: run {
                    noteImageView.hide()
                }

                itemView.setOnClickListener {
                    val intent = NoteEditorActivity.newIntent(itemView.context, noteWithImages.note.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    private class NoteDiffCallback : DiffUtil.ItemCallback<NoteWithImages>() {
        override fun areItemsTheSame(oldItem: NoteWithImages, newItem: NoteWithImages): Boolean {
            return oldItem.note.id == newItem.note.id
        }

        override fun areContentsTheSame(oldItem: NoteWithImages, newItem: NoteWithImages): Boolean {
            return oldItem == newItem
        }
    }
}
