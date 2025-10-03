package com.example.cs220_activity

import android.view.*
import androidx.recyclerview.widget.*

class GalleryPageAdapter(
    private val imageList: List<GalleryImage>,
    private val onImageClick: (Int) -> Unit
) : RecyclerView.Adapter<GalleryPageAdapter.PageViewHolder>() {

    private val itemsPerPage = 6
    private val pageAdapters = mutableMapOf<Int, GalleryAdapter>()
    private var listLayout: Boolean = false

    inner class PageViewHolder(imageView: View) : RecyclerView.ViewHolder(imageView) {
        val pageRecyclerView: RecyclerView = imageView.findViewById(R.id.rvGalleryPage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_page, parent, false)
        return PageViewHolder(view)
    }

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