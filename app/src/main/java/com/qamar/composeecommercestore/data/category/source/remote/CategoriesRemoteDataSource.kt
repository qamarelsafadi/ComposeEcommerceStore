package com.qamar.composeecommercestore.data.category.source.remote

import com.qamar.composeecommercestore.data.category.Category
import com.qamar.composeecommercestore.util.Resource
import com.qamar.composeecommercestore.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CategoriesRemoteDataSource @Inject constructor(
    private val api: CategoryApi
) : CategoryRemoteDataSource {

    override fun getCategoriesStream(): Flow<Result<List<Category>>> =
        MutableStateFlow(runBlocking { getCategories() })

    override suspend fun getCategories(): Result<List<Category>> {
        val data: Result<List<Category>> = try {
            val response = api.getCategory()
            if (response.isSuccessful) {
                Result.Success(response.body() ?: listOf())
            } else {
                Result.Error(Throwable(message = response.errorBody().toString()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error()

        }
        return data
    }


}
