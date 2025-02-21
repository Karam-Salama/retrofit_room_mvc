package com.example.mvc_meal_db.core.model.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvc_meal_db.core.model.CategoryModel

@Database(entities = [CategoryModel::class], version = 1)
abstract class CategoryDatabase : RoomDatabase() {
    abstract fun getCategoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: CategoryDatabase? = null

        fun getDatabase(ctx: Context): CategoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, // ✅ تجنب Memory Leak
                    CategoryDatabase::class.java,
                    "categories_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

