package com.example.noteapp.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun ImageView.loadImage(uri: String?) {
    uri?.let {
        Glide.with(context)
            .load(it)
            .into(this)
    }
}
