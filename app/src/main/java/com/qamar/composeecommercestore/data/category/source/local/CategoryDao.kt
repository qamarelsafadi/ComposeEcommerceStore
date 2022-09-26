
package com.qamar.composeecommercestore.data.category.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qamar.composeecommercestore.data.category.Category
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the tasks table.
 */
@Dao
interface CategoryDao {

    /**
     * Observes list of tasks.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM Categories")
    fun observeCategories(): Flow<List<Category>>

    /**
     * Select all tasks from the tasks table.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM Categories")
    suspend fun getCategories(): List<Category>

    /**
     * Insert a task in the database. If the task already exists, replace it.
     *
     * @param task the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(task: Category)


    /**
     * Delete all tasks.
     */
    @Query("DELETE FROM Categories")
    suspend fun deleteCategory()

}
