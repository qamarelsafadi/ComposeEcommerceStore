package com.qamar.composeecommercestore.data.category.source.local

import com.qamar.composeecommercestore.data.category.Category
import com.qamar.composeecommercestore.util.Result
import kotlinx.coroutines.flow.Flow


interface CategoryLocalDataSource {
    fun getCategoriesStream(): Flow<List<Category>>
    suspend fun getCategories(): Result<List<Category>>
    suspend fun deleteAllCategories()
    suspend fun saveCategory(category: Category)

}
