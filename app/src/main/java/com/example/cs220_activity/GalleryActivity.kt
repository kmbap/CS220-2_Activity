package com.example.cs220_activity

import android.content.Intent
import android.os.Bundle
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

class GalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gallery)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewPager = findViewById<ViewPager2>(R.id.vpPager)
        val switchLayout = findViewById<SwitchCompat>(R.id.switchLayout)
        val tbtnDesc = findViewById<ToggleButton>(R.id.tbtnDesc)
        val bnavMenu = findViewById<BottomNavigationView>(R.id.bnavMenu)

        val galleryImages = listOf(
            GalleryImage(R.drawable.cady_1, "Happy Cady", "Tuesday, May 20, 2025 | 21:29:21"),
            GalleryImage(R.drawable.cady_2, "Eepy Cady", "Tuesday, May 20, 2025 | 21:24:28"),
            GalleryImage(R.drawable.yuumi_1, "Elegant Yuumi", "Tuesday, May 20, 2025 | 21:29:27"),
            GalleryImage(R.drawable.dacy_1, "Goofy Dacy", "Tuesday, May 20, 2025 | 21:36:21"),
            GalleryImage(R.drawable.lucy_1, "Cutie Lucy", "Tuesday, May 20, 2025 | 21:27:47"),
            GalleryImage(R.drawable.rakki_1, "Confused Rakki", "Tuesday, May 20, 2025 | 21:26:31"),
            GalleryImage(R.drawable.ming_1, "The Creation of Ming", "Tuesday, May 20, 2025 | 21:29:21"),
            GalleryImage(R.drawable.chachi_1, "Hiding Chachi", "Tuesday, May 20, 2025 | 21:24:28"),
            GalleryImage(R.drawable.dacy_2, "Weirdo Dacy", "Tuesday, May 20, 2025 | 21:29:27"),
            GalleryImage(R.drawable.umi_1, "Shy Umi", "Tuesday, May 20, 2025 | 21:36:21"),
            GalleryImage(R.drawable.luca_1, "Side Eye Luca", "Tuesday, May 20, 2025 | 21:27:47"),
            GalleryImage(R.drawable.dacy_3, "Pretty Dacy", "Tuesday, May 20, 2025 | 21:26:31")
        )

        val galleryPageAdapter = GalleryPageAdapter(galleryImages) { position ->
            val modal = ImageDetailView(galleryImages, position)
            modal.show(supportFragmentManager, "ImageDetailView")
        }
        viewPager.adapter = galleryPageAdapter

        switchLayout.thumbTintList = ContextCompat.getColorStateList(this, R.color.switch_thumb)
        switchLayout.trackTintList = ContextCompat.getColorStateList(this, R.color.arc3)

        switchLayout.isChecked = false
        galleryPageAdapter.setListLayout(false)

        switchLayout.setOnCheckedChangeListener { _, isChecked ->
            galleryPageAdapter.setListLayout(isChecked)
        }

        tbtnDesc.setOnCheckedChangeListener { _, isChecked ->
            (viewPager.adapter as? GalleryPageAdapter)?.let {
                it.showDescOnAllPages(isChecked)
            }
        }

        bnavMenu.selectedItemId = R.id.bnavGallery
        bnavMenu.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bnavGallery -> { true }
                R.id.bnavLogout -> {
                    startActivity(Intent(this, LoginActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    })
                    true
                }
                else -> false
            }
        }
    }
}