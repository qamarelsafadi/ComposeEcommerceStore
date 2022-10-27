package com.qamar.composeecommercestore.data.category.source.remote

import com.qamar.composeecommercestore.data.category.model.Category
import com.qamar.composeecommercestore.util.Result
import kotlinx.coroutines.flow.Flow


interface CategoryRemoteDataSource {
    fun getCategoriesStream(): Flow<Result<List<Category>>>
    suspend fun getCategories(): Result<List<Category>>
}
