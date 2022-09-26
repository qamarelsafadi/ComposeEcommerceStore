package com.qamar.composeecommercestore.data.category.source

import com.qamar.composeecommercestore.data.Result
import com.qamar.composeecommercestore.data.category.Category
import com.qamar.composeecommercestore.util.Resource
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategoriesStream(): Flow<Resource<List<Category>>>
    suspend fun getCategories(forceUpdate: Boolean = false): Resource<List<Category>>
}