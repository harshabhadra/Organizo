package com.harshabhadra.organizo.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(name: Category)

    @Insert
    fun insertListOfCategory(category: List<Category>)

    @Query("SELECT * FROM category_table ORDER BY category_name DESC")
    fun getCategories():LiveData<List<Category>>

    @Delete
    fun removeCategory(category: Category)
}