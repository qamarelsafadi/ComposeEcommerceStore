package com.qamar.composeecommercestore.data.category.source

import com.qamar.composeecommercestore.data.category.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategoriesStream(): Flow<List<Category>>
    fun getCategory(): Flow<com.qamar.composeecommercestore.util.Result<List<Category>>>
}