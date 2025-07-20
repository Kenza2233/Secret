package com.example.noteapp.view

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.noteapp.databinding.DialogColorPickerBinding

class ColorPickerDialogFragment(
    private val onColorSelected: (String) -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogColorPickerBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogColorPickerBinding.inflate(layoutInflater)

        val colors = generateColors()
        val adapter = ColorAdapter(colors) { color ->
            onColorSelected(color)
            dismiss()
        }

        binding.colorRecyclerView.adapter = adapter
        binding.colorRecyclerView.layoutManager = GridLayoutManager(context, 9)

        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    private fun generateColors(): List<String> {
        // Logik untuk menjana 99 warna akan ditambah di sini
        // Buat masa ini, kita guna senarai warna rekaan
        return listOf(
            "#FFFFFF", "#FFCDD2", "#F8BBD0", "#E1BEE7", "#D1C4E9", "#C5CAE9", "#BBDEFB", "#B3E5FC", "#B2EBF2",
            "#B2DFDB", "#C8E6C9", "#DCEDC8", "#F0F4C3", "#FFF9C4", "#FFECB3", "#FFE0B2", "#FFCCBC", "#D7CCC8",
            "#F5F5F5", "#CFD8DC", "#FF8A80", "#FF80AB", "#EA80FC", "#B388FF", "#8C9EFF", "#82B1FF", "#80D8FF",
            "#84FFFF", "#A7FFEB", "#B9F6CA", "#CCFF90", "#F4FF81", "#FFFF8D", "#FFE57F", "#FFD180", "#FF9E80",
            "#EFEBE9", "#E0E0E0", "#B0BEC5", "#FF5252", "#FF4081", "#E040FB", "#7C4DFF", "#536DFE", "#448AFF",
            "#40C4FF", "#18FFFF", "#64FFDA", "#69F0AE", "#B2FF59", "#EEFF41", "#FFFF00", "#FFD740", "#FFAB40",
            "#FF6E40", "#BCAAA4", "#BDBDBD", "#90A4AE", "#D32F2F", "#C2185B", "#7B1FA2", "#512DA8", "#303F9F",
            "#1976D2", "#0288D1", "#0097A7", "#00796B", "#388E3C", "#689F38", "#AFB42B", "#FBC02D", "#FFA000",
            "#F57C00", "#E64A19", "#795548", "#616161", "#455A64", "#B71C1C", "#880E4F", "#4A148C", "#311B92",
            "#1A237E", "#0D47A1", "#01579B", "#006064", "#004D40", "#1B5E20", "#33691E", "#827717", "#F57F17"
        )
    }
}
