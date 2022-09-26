package com.qamar.composeecommercestore.data.category.source.remote

import com.qamar.composeecommercestore.data.category.Category
import com.qamar.composeecommercestore.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CategoriesRemoteDataSource @Inject constructor(
    private val api: CategoryApi
) : CategoryRemoteDataSource {

    override fun getCategoriesStream(): Flow<Resource<List<Category>>> =
        MutableStateFlow(runBlocking { getCategories() })

    override suspend fun getCategories(): Resource<List<Category>> {
        val data: Resource<List<Category>> = try {
            val response = api.getCategory()
            if (response.isSuccessful) {
                Resource.success(response.body() ?: listOf())
            } else {
                Resource.error(message = response.errorBody().toString(), data = null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(message = e.message, data = null)

        }
        return data
    }


}
