package com.example.mvc_meal_db.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category_table")
data class CategoryModel(
  @PrimaryKey(autoGenerate = false)
  val idCategory: String,
  val strCategory: String?,
  val strCategoryDescription: String?,
  val strCategoryThumb: String?
)
