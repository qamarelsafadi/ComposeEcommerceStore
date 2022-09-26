package com.qamar.composeecommercestore.data.category.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.qamar.composeecommercestore.data.category.Category

/**
 * The Room Database that contains the Task table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [Category::class], version = 1, exportSchema = false)
abstract class CategoriesDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
}
