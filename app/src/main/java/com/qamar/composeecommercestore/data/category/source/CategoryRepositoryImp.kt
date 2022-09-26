package com.qamar.composeecommercestore.data.category.source

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

    private suspend fun updateTasksFromRemoteDataSource() {
        val remoteTasks = categoryRemoteDataSource.getCategories()
        if (remoteTasks.status == Status.SUCCESS) {
            categoryLocalDataSource.deleteAllCategories()
            remoteTasks.data?.forEach { task ->
                categoryLocalDataSource.saveCategory(task)
            }
        } else if (remoteTasks.status == Status.ERROR) {
        }
    }

    override fun getCategoriesStream(): Flow<Resource<List<Category>>> {
        return if (categoryLocalDataSource.isEmpty())
            categoryRemoteDataSource.getCategoriesStream()
        else categoryLocalDataSource.getCategoriesStream()
    }

    override suspend fun getCategories(forceUpdate: Boolean): Resource<List<Category>> {
        if (forceUpdate) {
            try {
                updateTasksFromRemoteDataSource()
            } catch (ex: Exception) {
                return Resource.error(message = ex.message, data = null)
            }
        }
        return categoryLocalDataSource.getCategories()
    }
}

