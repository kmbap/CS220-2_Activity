package com.example.cs220_activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // NavBar Template
//        bnavMenu.selectedItemId = R.id.bnavGallery
//        bnavMenu.setOnItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.bnavGallery -> { true }
//                R.id.bnavLogout -> {
//                    startActivity(Intent(this, LoginActivity::class.java).apply {
//                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                    })
//                    true
//                }
//                else -> false
//            }
//        }

        // Redirect to Gallery
        startActivity(Intent(this@DashboardActivity, GalleryActivity::class.java))
    }
}