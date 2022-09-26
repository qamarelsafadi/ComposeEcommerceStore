package com.qamar.composeecommercestore.data.category.source.local

import com.qamar.composeecommercestore.data.Result
import com.qamar.composeecommercestore.data.category.Category
import com.qamar.composeecommercestore.util.Resource
import kotlinx.coroutines.flow.Flow


interface CategoryLocalDataSource {
    fun getCategoriesStream(): Flow<Resource<List<Category>>>
    fun isEmpty(): Boolean
    suspend fun getCategories(): Resource<List<Category>>
    suspend fun deleteAllCategories()
    suspend fun saveCategory(category: Category)

}
