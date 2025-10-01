package com.example.cs220_activity1_signup

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GalleryActivity : AppCompatActivity() {
    private lateinit var rvGallery: RecyclerView
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var galleryImages: List<GalleryImage>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gallery)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rvImageGallery = findViewById<RecyclerView>(R.id.rvImageGallery)

        galleryImages = listOf(
            GalleryImage(R.drawable.cady_1, "Happy Cady", "Tuesday, May 20, 2025 | 21:29:21"),
            GalleryImage(R.drawable.cady_2, "Eepy Cady", "Tuesday, May 20, 2025 | 21:24:28"),
            GalleryImage(R.drawable.cady_3
                , "Sniffy Cady", "Tuesday, May 20, 2025 | 21:29:27"),
            GalleryImage(R.drawable.dacy_1, "Goofy Dacy", "Tuesday, May 20, 2025 | 21:36:21"),
            GalleryImage(R.drawable.lucy_1, "Cutie Lucy", "Tuesday, May 20, 2025 | 21:27:47"),
            GalleryImage(R.drawable.rakki_1, "Confused Rakki", "Tuesday, May 20, 2025 | 21:26:31"),
        )

        rvImageGallery.layoutManager = GridLayoutManager(this, 2)

        galleryAdapter = GalleryAdapter(galleryImages) { clickedImage ->
            val modal = ImageDetailView(clickedImage.imgResId, clickedImage.title, clickedImage.createdAt)
            modal.show(supportFragmentManager, "ImageDetailView")
        }
        rvImageGallery.adapter = galleryAdapter
    }
}