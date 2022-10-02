package com.qamar.composeecommercestore.data.category.source

import com.qamar.composeecommercestore.data.category.Category
import com.qamar.composeecommercestore.util.Resource
import com.qamar.composeecommercestore.util.Result
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun getCategoriesStream(): Flow<List<Category>>
    suspend fun getCategories(forceUpdate: Boolean = false): Result<List<Category>>
}