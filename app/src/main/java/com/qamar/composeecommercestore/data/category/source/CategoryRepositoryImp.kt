package com.qamar.composeecommercestore.data.category.source

import android.util.Log
import com.qamar.composeecommercestore.data.category.Category
import com.qamar.composeecommercestore.data.category.source.local.CategoryLocalDataSource
import com.qamar.composeecommercestore.data.category.source.remote.CategoryRemoteDataSource
import com.qamar.composeecommercestore.util.Resource
import com.qamar.composeecommercestore.util.Result
import com.qamar.composeecommercestore.util.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty

class CategoryRepositoryImp(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val categoryLocalDataSource: CategoryLocalDataSource
) : CategoryRepository {

    private suspend fun updateCategoriesFromRemoteDataSource(): Flow<List<Category>> {
        val remoteCategories = categoryRemoteDataSource.getCategories()
        if (remoteCategories is Result.Success) {
            categoryLocalDataSource.deleteAllCategories()
            remoteCategories.data.forEach { category ->
                categoryLocalDataSource.saveCategory(category)
            }
        }
        return categoryLocalDataSource.getCategoriesStream()
    }

    override suspend fun getCategoriesStream(): Flow<List<Category>> {
        updateCategoriesFromRemoteDataSource()
        return categoryLocalDataSource.getCategoriesStream()
    }

    override suspend fun getCategories(forceUpdate: Boolean): Result<List<Category>> {
        return categoryLocalDataSource.getCategories()
    }
}

