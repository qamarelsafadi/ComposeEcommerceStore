package com.qamar.composeecommercestore.data.category.source

import com.qamar.composeecommercestore.data.category.model.Category
import com.qamar.composeecommercestore.data.category.source.local.CategoryLocalDataSource
import com.qamar.composeecommercestore.data.category.source.remote.CategoryRemoteDataSource
import com.qamar.composeecommercestore.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

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
        // TODO : need someone to tell me if this is a good approach
      val getFromNetwork = categoryLocalDataSource.getCategoriesStream().map {
            it.isEmpty()
        }
        if (getFromNetwork.first()) {
            try {
                updateCategoriesFromRemoteDataSource()
            } catch (ex: Exception) {
                ex.printStackTrace()
                return MutableStateFlow(listOf())
            }
        }
        return categoryLocalDataSource.getCategoriesStream()
    }

}

