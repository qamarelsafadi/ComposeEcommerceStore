package com.qamar.composeecommercestore.data.category.source.local

import com.qamar.composeecommercestore.data.category.Category
import com.qamar.composeecommercestore.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CategoriesLocalDataSource internal constructor(
    private val categoryDao: CategoryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoryLocalDataSource {
    override fun getCategoriesStream(): Flow<Resource<List<Category>>> {
        return categoryDao.observeCategories().map {
            Resource.success(it)
        }
    }

    override suspend fun isEmpty(): Boolean {
        return getCategories().data?.isNullOrEmpty() ?: true
    }

    override suspend fun getCategories(): Resource<List<Category>> = withContext(ioDispatcher) {
        val data: Resource<List<Category>> = try {
            Resource.success(categoryDao.getCategories())
        } catch (e: Exception) {
            Resource.error(data = null, e.message)
        }
        data
    }

    override suspend fun deleteAllCategories() {
        categoryDao.deleteCategory()
    }

    override suspend fun saveCategory(category: Category) {
        categoryDao.insertCategory(category)
    }
}
