package com.example.mvc_meal_db.features.fav.controller

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvc_meal_db.R
import com.example.mvc_meal_db.core.model.CategoryListener
import com.example.mvc_meal_db.core.model.CategoryModel
import com.example.mvc_meal_db.core.model.cache.CategoryDao
import com.example.mvc_meal_db.core.model.cache.CategoryDatabase
import com.example.mvc_meal_db.core.view.CategoryAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteActivity : AppCompatActivity(), CategoryListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryAdapter
    private lateinit var categoryDao: CategoryDao

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favorite)

        recyclerView = findViewById(R.id.rc_favorite)
        adapter = CategoryAdapter(listOf(), recyclerView.context, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        categoryDao = CategoryDatabase.getDatabase(this).getCategoryDao()

        fetchFavorites()
    }

    private fun fetchFavorites() {
        lifecycleScope.launch {
            var storedCategories: List<CategoryModel>
            try {
                withContext(Dispatchers.IO) {
                    storedCategories = categoryDao.getAllCategories()
                }
                adapter.categories = storedCategories
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("===>", "Error Fetching Favorites", e)
                }
            }
        }
    }

    override fun onCategoryClick(category: CategoryModel) {
        lifecycleScope.launch(Dispatchers.IO) {
            val result = categoryDao.removeCategory(category)

            withContext(Dispatchers.Main) {
                if (result > 0) {
                    Snackbar.make(recyclerView, "Removed ${category.strCategory}", Snackbar.LENGTH_SHORT).show()
                    fetchFavorites()
                } else {
                    Snackbar.make(recyclerView, "Couldn't remove ${category.strCategory}", Snackbar.LENGTH_SHORT).show()
                }
            }
            val newList = categoryDao.getAllCategories()
            withContext(Dispatchers.Main) {
                adapter.categories = newList
                adapter.notifyDataSetChanged()
            }
        }
    }
}
