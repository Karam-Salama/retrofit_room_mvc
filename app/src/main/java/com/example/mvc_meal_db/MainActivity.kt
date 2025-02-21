package com.example.mvc_meal_db

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mvc_meal_db.features.categories.controller.CategoriesActivity
import com.example.mvc_meal_db.features.fav.controller.FavoriteActivity

class MainActivity : AppCompatActivity() {
    private lateinit var btnAllCategories: Button
    private lateinit var btnFav: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initViews()
        setListeners()
    }

    private fun initViews() {
        btnAllCategories = findViewById(R.id.btn_all_categories)
        btnFav = findViewById(R.id.btn_favorites)
    }

    private fun setListeners() {
        btnAllCategories.setOnClickListener {
            startActivity(Intent(this, CategoriesActivity::class.java))
        }
        btnFav.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
    }
}
