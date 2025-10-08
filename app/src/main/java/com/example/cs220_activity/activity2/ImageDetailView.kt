package com.example.cs220_activity.activity2

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.cs220_activity.R

class ImageDetailView(
    private val images: List<GalleryImage>,
    private var cIndex: Int)
    : DialogFragment() {

    // Creates modal window
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val modal = super.onCreateDialog(savedInstanceState)
        modal.window?.setBackgroundDrawableResource(R.color.arcW)
        return modal
    }

    // Determines layout and position of elements based on layout file
    // Puts data into UI elements and handles interactions
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.image_detail_view, container, false)

        val ivImg = view.findViewById<ImageView>(R.id.ivImg)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvCreated = view.findViewById<TextView>(R.id.tvCreated)

        val sbNavigate = view.findViewById<SeekBar>(R.id.sbNavigate)

        val ibtnClose = view.findViewById<ImageButton>(R.id.ibtnClose)

        // Images Navigator
        sbNavigate.max = images.size - 1
        sbNavigate.progress = cIndex

        fun showImg (index: Int) {
            val img = images[index]
            ivImg.setImageResource(img.imgResId)
            tvTitle.text = img.title
            tvCreated.text = img.createdAt
        }
        showImg(cIndex)

        sbNavigate.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    cIndex = progress
                    showImg(cIndex)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        ibtnClose.setOnClickListener { dismiss() }

        return view
    }
}