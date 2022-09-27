package com.qamar.composeecommercestore.data.category.source

import android.util.Log
import com.qamar.composeecommercestore.data.category.Category
import com.qamar.composeecommercestore.data.category.source.local.CategoryLocalDataSource
import com.qamar.composeecommercestore.data.category.source.remote.CategoryRemoteDataSource
import com.qamar.composeecommercestore.util.Resource
import com.qamar.composeecommercestore.util.Status
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImp(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val categoryLocalDataSource: CategoryLocalDataSource
) : CategoryRepository {

    private suspend fun updateCategoriesFromRemoteDataSource(): Resource<List<Category>> {
        val remoteTasks = categoryRemoteDataSource.getCategories()
        if (remoteTasks.status == Status.SUCCESS) {
            categoryLocalDataSource.deleteAllCategories()
            remoteTasks.data?.forEach { category ->
                categoryLocalDataSource.saveCategory(category)
            }
        }
        return categoryLocalDataSource.getCategories()
    }

    override fun getCategoriesStream(): Flow<Resource<List<Category>>> {
        return categoryLocalDataSource.getCategoriesStream()
    }

    override suspend fun getCategories(forceUpdate: Boolean): Resource<List<Category>> {
        return if (categoryLocalDataSource.isEmpty() && forceUpdate)
            updateCategoriesFromRemoteDataSource()
        else categoryLocalDataSource.getCategories()
    }
}

