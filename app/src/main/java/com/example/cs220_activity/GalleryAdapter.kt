package com.example.cs220_activity

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class GalleryAdapter(
    private var imageList: List<GalleryImage>,
    private var isListLayout: Boolean = false,
    private val onImageClick: (Int) -> Unit
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    var showDesc: Boolean = false
    var showImg: Boolean = false

    fun setListLayout(list: Boolean) {
        isListLayout = list
    }

    class ViewHolder(imageView: View) : RecyclerView.ViewHolder(imageView) {
        val ivImage: ImageView? = imageView.findViewById(R.id.ivImage) ?: imageView.findViewById(R.id.ivImgList)
        val tvDesc: TextView? = imageView.findViewById(R.id.tvDesc)
        val tvTitle: TextView? = imageView.findViewById(R.id.tvTitleList)
        val tvCreatedLabel: TextView? = imageView.findViewById(R.id.tvLblCreatedList)
        val tvCreated: TextView? = imageView.findViewById(R.id.tvCreatedList)
        val pbarLoading: ProgressBar? = imageView.findViewById(R.id.pbarLoading)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutRes = if (isListLayout) R.layout.gallery_image_list else R.layout.gallery_image
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = imageList[position]
        holder.ivImage?.setImageResource(image.imgResId)
        holder.tvTitle?.text = image.title
        holder.tvCreated?.text = image.createdAt

        holder.tvDesc?.let {
            it.visibility = if (showDesc) View.VISIBLE else View.GONE
            if (showDesc) it.text = image.title
        }

        holder.tvCreatedLabel?.visibility = if (showDesc) View.VISIBLE else View.GONE
        holder.tvCreated?.visibility = if (showDesc) View.VISIBLE else View.GONE

        holder.pbarLoading?.visibility = View.VISIBLE

        if (showImg) {
            holder.ivImage?.visibility = View.VISIBLE
            holder.pbarLoading?.visibility = View.GONE
        } else holder.ivImage?.visibility = View.INVISIBLE

        holder.ivImage?.postDelayed({
            holder.pbarLoading?.visibility = View.GONE
            holder.ivImage?.visibility = View.VISIBLE
            showImg = true
        }, 2000)

        holder.ivImage?.post {
            val width = holder.ivImage?.width ?: return@post
            holder.ivImage.layoutParams?.let { lp ->
                lp.height = width
                holder.ivImage.requestLayout()
            }
        }

        holder.itemView.setOnClickListener { onImageClick(position) }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}