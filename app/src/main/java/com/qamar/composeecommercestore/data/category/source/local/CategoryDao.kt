
package com.qamar.composeecommercestore.data.category.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qamar.composeecommercestore.data.category.Category
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the categories table.
 */
@Dao
interface CategoryDao {

    /**
     * Observes list of categories.
     *
     * @return all categories.
     */
    @Query("SELECT * FROM Categories")
    fun observeCategories(): Flow<List<Category>>

    /**
     * Select all categories from the categories table.
     *
     * @return all categories.
     */
    @Query("SELECT * FROM Categories")
    suspend fun getCategories(): List<Category>

    /**
     * Insert a category in the database. If the task already exists, replace it.
     *
     * @param task the category to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(task: Category)


    /**
     * Delete all categories.
     */
    @Query("DELETE FROM Categories")
    suspend fun deleteCategory()

}
