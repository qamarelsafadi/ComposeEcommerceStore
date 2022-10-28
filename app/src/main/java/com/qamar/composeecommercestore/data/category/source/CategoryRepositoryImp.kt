package com.qamar.composeecommercestore.data.category.source

import android.util.Log
import com.qamar.composeecommercestore.data.category.model.Category
import com.qamar.composeecommercestore.data.category.source.local.CategoryLocalDataSource
import com.qamar.composeecommercestore.data.category.source.remote.CategoryRemoteDataSource
import com.qamar.composeecommercestore.util.Result
import com.qamar.composeecommercestore.util.asResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class CategoryRepositoryImp(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val categoryLocalDataSource: CategoryLocalDataSource
) : CategoryRepository {

    private suspend fun updateCategoriesFromRemoteDataSource() {
        val remoteCategories = categoryRemoteDataSource.getCategories()
        if (remoteCategories is Result.Success) {
            categoryLocalDataSource.deleteAllCategories()
            remoteCategories.data.forEach { category ->
                categoryLocalDataSource.saveCategory(category)
            }
        }
    }

    override suspend fun getCategoriesStream(): Flow<List<Category>> {
        val getFromNetwork = categoryLocalDataSource.getCategoriesStream().map {
            it.isEmpty()
        }
        if (getFromNetwork.first()) {
            Log.e("qmr", "cat heeey")
            try {
                updateCategoriesFromRemoteDataSource()
            } catch (ex: Exception) {
                ex.printStackTrace()
                return MutableStateFlow(listOf())
            }
        }
        Log.e("qmr", "cat heeey")

        return categoryLocalDataSource.getCategoriesStream()
    }

    override fun getCategory(): Flow<Result<List<Category>>> {
        return categoryLocalDataSource.getCategoriesStream().asResult()
    }

}

