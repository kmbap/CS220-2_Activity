package com.example.cs220_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GalleryAdapter(
    private var imageList: List<GalleryImage>,
    private val onImageClick: (Int) -> Unit
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    var showDesc: Boolean = false
    var showImg: Boolean = false

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage: ImageView = itemView.findViewById(R.id.ivImage)
        val tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
        val pbarLoading: ProgressBar = itemView.findViewById(R.id.pbarLoading)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = imageList[position]
        holder.ivImage.setImageResource(image.imgResId)
        holder.tvDesc.text = image.title

        holder.pbarLoading.visibility = View.VISIBLE

        if (showImg) {
            holder.ivImage.visibility = View.VISIBLE
            holder.pbarLoading.visibility = View.GONE
        } else holder.ivImage.visibility = View.INVISIBLE

        holder.ivImage.postDelayed({
            holder.pbarLoading.visibility = View.GONE
            holder.ivImage.visibility = View.VISIBLE
            showImg = true
        }, 2000)

        holder.ivImage.post {
            val width = holder.ivImage.width
            holder.ivImage.layoutParams.height = width
            holder.ivImage.requestLayout()
        }

        holder.itemView.setOnClickListener {
            onImageClick(holder.adapterPosition)
        }

        holder.tvDesc.visibility = if (showDesc) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}