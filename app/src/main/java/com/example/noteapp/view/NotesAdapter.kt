package com.example.noteapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.example.noteapp.databinding.ItemNoteBinding
import com.example.noteapp.model.NoteWithImages

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

                if (noteWithImages.images.isNotEmpty()) {
                    noteImageView.visibility = View.VISIBLE
                    Glide.with(itemView.context)
                        .load(noteWithImages.images[0].uri)
                        .into(noteImageView)
                } else {
                    noteImageView.visibility = View.GONE
                }

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, NoteEditorActivity::class.java).apply {
                        putExtra(NoteEditorActivity.EXTRA_NOTE_ID, noteWithImages.note.id)
                    }
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
