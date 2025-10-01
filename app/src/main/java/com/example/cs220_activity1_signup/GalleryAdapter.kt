package com.example.cs220_activity1_signup

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class GalleryAdapter(
    private var imageList: List<GalleryImage>,
    private val onImageClick: (GalleryImage) -> Unit)
    : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

        var showDesc: Boolean = false

    // References UI Elements
    class ViewHolder(imageView: View): RecyclerView.ViewHolder(imageView) {
        val ivImage: ImageView = imageView.findViewById(R.id.ivImage)
        val tvDesc: TextView = imageView.findViewById(R.id.tvDesc)
    }

    // Sets Layout Of UI Elements
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_image, parent, false)
        return ViewHolder(view)
    }

    // Sets Values of UI Elements
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = imageList[position]
        holder.ivImage.setImageResource(image.imgResId)
        holder.tvDesc.text = image.title

        holder.ivImage.post {
            val width = holder.ivImage.width
            holder.ivImage.layoutParams.height = width
            holder.ivImage.requestLayout()
        }

        holder.ivImage.setOnClickListener {
            onImageClick(image)
        }

        holder.tvDesc.visibility = if (showDesc) View.VISIBLE else View.INVISIBLE
    }

    override fun getItemCount(): Int = imageList.size
}