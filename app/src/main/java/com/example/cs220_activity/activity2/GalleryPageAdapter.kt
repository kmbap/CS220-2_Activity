package com.example.cs220_activity.activity2

import android.view.*
import androidx.recyclerview.widget.*
import com.example.cs220_activity.R

class GalleryPageAdapter(
    private val imageList: List<GalleryImage>,
    private val onImageClick: (Int) -> Unit
) : RecyclerView.Adapter<GalleryPageAdapter.PageViewHolder>() {

    // Variables
    private val itemsPerPage = 6
    private val pageAdapters = mutableMapOf<Int, GalleryAdapter>()
    private var listLayout: Boolean = false

    // Determines UI elements included
    inner class PageViewHolder(imageView: View) : RecyclerView.ViewHolder(imageView) {
        val pageRecyclerView: RecyclerView = imageView.findViewById(R.id.rvGalleryPage)
    }

    // Determines layout and position of elements based on layout file
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_page, parent, false)
        return PageViewHolder(view)
    }

    // Puts data into UI elements and handles interactions
    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val start = position * itemsPerPage
        val end = minOf(start + itemsPerPage, imageList.size)
        val imageSubList = imageList.subList(start, end)

        val galleryAdapter = GalleryAdapter(imageSubList, listLayout) { subPosition ->
            onImageClick(start + subPosition)
        }
        pageAdapters[position] = galleryAdapter

        holder.pageRecyclerView.layoutManager =
            if (listLayout) LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
            else GridLayoutManager(holder.itemView.context, 2, GridLayoutManager.VERTICAL, false)
        holder.pageRecyclerView.adapter = galleryAdapter
    }

    // Page Count
    override fun getItemCount(): Int {
        return (imageList.size + itemsPerPage - 1) / itemsPerPage
    }

    fun showDescOnAllPages(show: Boolean) {
        for (adapter in pageAdapters.values) {
            adapter.showDesc = show
            adapter.notifyDataSetChanged()
        }
    }

    fun setListLayout(isList: Boolean) {
        listLayout = isList
        for (adapter in pageAdapters.values) {
            adapter.setListLayout(isList)
            adapter.notifyDataSetChanged()
        }
        notifyDataSetChanged()
    }
}