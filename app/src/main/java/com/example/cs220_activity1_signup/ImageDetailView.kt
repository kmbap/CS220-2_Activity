package com.example.cs220_activity1_signup

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class ImageDetailView(
    private val imgResId: Int,
    private val title: String,
    private val created: String)
    : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val modal = super.onCreateDialog(savedInstanceState)
        modal.window?.setBackgroundDrawableResource(R.color.arcW)
        return modal
    }

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