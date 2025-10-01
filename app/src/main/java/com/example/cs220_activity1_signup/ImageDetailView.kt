package com.example.cs220_activity1_signup

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.DialogFragment

class ImageDetailView(
    private val imgResId: Int,
    private val title: String,
    private val created: String)
    : DialogFragment() {

        // Creates Modal Window
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val modal = super.onCreateDialog(savedInstanceState)
        modal.window?.setBackgroundDrawableResource(R.color.arcW)
        return modal
    }

    // Lays Out UI Elements
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.image_detail_view, container, false)

        val ivImg = view.findViewById<ImageView>(R.id.ivImg)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvCreated = view.findViewById<TextView>(R.id.tvCreated)

        val ibtnClose = view.findViewById<ImageButton>(R.id.ibtnClose)

        ivImg.setImageResource(imgResId)
        tvTitle.text = title
        tvCreated.text = created

        ibtnClose.setOnClickListener {
            dismiss()
        }

        return view
    }
}