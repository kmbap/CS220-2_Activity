package com.example.cs220_activity1_signup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GalleryAdapter(
    private val imageList: List<GalleryImage>,
    private val onImageClick: (GalleryImage) -> Unit)
    : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    class ViewHolder(imageView: View): RecyclerView.ViewHolder(imageView) {
        val ivImage: ImageView = imageView.findViewById(R.id.ivImage)
        val tvDesc: TextView = imageView.findViewById(R.id.tvDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_image, parent, false)
        return ViewHolder(view)
    }

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
    }

    override fun getItemCount(): Int = imageList.size
}