package com.qamar.composeecommercestore.data.category.source.remote

import com.qamar.composeecommercestore.data.Result
import com.qamar.composeecommercestore.data.category.Category
import com.qamar.composeecommercestore.util.Resource
import kotlinx.coroutines.flow.Flow


interface CategoryRemoteDataSource {
    fun getCategoriesStream(): Flow<Resource<List<Category>>>
    suspend fun getCategories(): Resource<List<Category>>
}
