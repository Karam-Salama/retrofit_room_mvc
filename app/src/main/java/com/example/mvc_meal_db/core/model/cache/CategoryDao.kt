package com.example.mvc_meal_db.core.model.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvc_meal_db.core.model.CategoryModel

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category_table")
    suspend fun getAllCategories(): List<CategoryModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCategory(category: CategoryModel): Long

    @Delete
    suspend fun removeCategory(category: CategoryModel): Int
}
