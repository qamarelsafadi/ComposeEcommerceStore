package com.qamar.composeecommercestore.data.category.source.local

import com.qamar.composeecommercestore.data.category.model.Category
import com.qamar.composeecommercestore.data.category.model.asExternalModel
import com.qamar.composeecommercestore.util.Resource
import com.qamar.composeecommercestore.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class CategoriesLocalDataSource internal constructor(
    private val categoryDao: CategoryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoryLocalDataSource {
    override fun getCategoriesStream(): Flow<List<Category>> {
        return categoryDao.observeCategories().map { entityMovies ->
            entityMovies.map {
                it.asExternalModel()
            }
        }
    }


override suspend fun getCategories(): Result<List<Category>> = withContext(ioDispatcher) {
    val data: Result<List<Category>> = try {
        Result.Success(categoryDao.getCategories())
    } catch (e: Exception) {
        Result.Error(Throwable(e.message))
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
