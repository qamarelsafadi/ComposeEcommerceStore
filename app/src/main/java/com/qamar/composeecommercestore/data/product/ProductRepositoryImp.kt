package com.qamar.composeecommercestore.data.product

import com.qamar.composeecommercestore.data.product.model.Product
import com.qamar.composeecommercestore.data.product.source.remote.ProductRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import com.qamar.composeecommercestore.util.Result
import com.qamar.composeecommercestore.util.asResult

class ProductRepositoryImp(
    private val productRemoteDataSource: ProductRemoteDataSource,
) : ProductRepository {

    override suspend fun getProductByCategoryId(id: Int): Result<List<Product>> {
        return productRemoteDataSource.getProductByCategoryId(id)
    }
}

