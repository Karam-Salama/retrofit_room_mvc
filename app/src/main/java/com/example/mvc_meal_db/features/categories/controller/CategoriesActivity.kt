package com.example.mvc_meal_db.features.categories.controller

import android.content.ContentValues.TAG
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
import com.example.mvc_meal_db.core.model.server.RetrofitHelper
import com.example.mvc_meal_db.core.view.CategoryAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class CategoriesActivity : AppCompatActivity(), CategoryListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryAdapter
    private lateinit var categoryDao: CategoryDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_categories)

        recyclerView = findViewById(R.id.rc_categories)
        adapter = CategoryAdapter(listOf(), recyclerView.context, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        categoryDao = CategoryDatabase.getDatabase(this).getCategoryDao()

        fetchCategories()
    }

    private fun fetchCategories() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitHelper.retrofitService.getCategories()
                val categories = response.categories

                withContext(Dispatchers.Main) {
                    adapter.categories = categories
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("===>", "Error Fetching Categories", e)
                }
            }
        }
    }

    override fun onCategoryClick(category: CategoryModel) {
        lifecycleScope.launch(Dispatchers.IO) {
            val addingResult = categoryDao.addCategory(category)

            withContext(Dispatchers.Main) {
                if (addingResult > 0) {
                    Snackbar.make(recyclerView, "Added to Favorites ${category.strCategory}", Snackbar.LENGTH_SHORT).show()

                } else {
                    Snackbar.make(recyclerView, "${category.strCategory} is already in Favorites", Snackbar.LENGTH_SHORT).show()
                }
            }

        }
    }
}
