package com.example.cs220_activity1_signup

import android.os.Bundle
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.recyclerview.widget.*

class GalleryActivity : AppCompatActivity() {
    private lateinit var rvImageGallery: RecyclerView
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var galleryImages: List<GalleryImage>
    private lateinit var switchLayout: SwitchCompat
    private lateinit var lmGrid: GridLayoutManager
    private lateinit var lmList: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gallery)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Variables
        rvImageGallery = findViewById<RecyclerView>(R.id.rvImageGallery)
        switchLayout = findViewById<SwitchCompat>(R.id.switchLayout)
        val tbtnDesc = findViewById<ToggleButton>(R.id.tbtnDesc)

        // Images
        galleryImages = listOf(
            GalleryImage(R.drawable.cady_1, "Happy Cady", "Tuesday, May 20, 2025 | 21:29:21"),
            GalleryImage(R.drawable.cady_2, "Eepy Cady", "Tuesday, May 20, 2025 | 21:24:28"),
            GalleryImage(R.drawable.cady_3
                , "Sniffy Cady", "Tuesday, May 20, 2025 | 21:29:27"),
            GalleryImage(R.drawable.dacy_1, "Goofy Dacy", "Tuesday, May 20, 2025 | 21:36:21"),
            GalleryImage(R.drawable.lucy_1, "Cutie Lucy", "Tuesday, May 20, 2025 | 21:27:47"),
            GalleryImage(R.drawable.rakki_1, "Confused Rakki", "Tuesday, May 20, 2025 | 21:26:31"),
        )

        // Layout Managers
        lmGrid = GridLayoutManager(this, 2)
        lmList = LinearLayoutManager(this)

        // Image Layout
        rvImageGallery.layoutManager = lmGrid

        galleryAdapter = GalleryAdapter(galleryImages) { clickedImage ->
            val modal = ImageDetailView(clickedImage.imgResId, clickedImage.title, clickedImage.createdAt)
            modal.show(supportFragmentManager, "ImageDetailView")
        }
        rvImageGallery.adapter = galleryAdapter

        // Switch Colors & Listener
        switchLayout.thumbTintList = ContextCompat.getColorStateList(this, R.color.switch_thumb)
        switchLayout.trackTintList = ContextCompat.getColorStateList(this, R.color.arc3)

        switchLayout.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                rvImageGallery.layoutManager = lmList
                switchLayout.text = "Grid"
            } else {
                rvImageGallery.layoutManager = lmGrid
                switchLayout.text = "List"
            }
        }

        // Toggle Button Listener
        tbtnDesc.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) galleryAdapter.showDesc = true
            else galleryAdapter.showDesc = false

            galleryAdapter.notifyDataSetChanged()
        }
    }
}