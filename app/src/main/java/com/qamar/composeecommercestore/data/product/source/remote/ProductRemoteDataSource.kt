package com.qamar.composeecommercestore.data.product.source.remote

import com.qamar.composeecommercestore.data.category.model.Category
import com.qamar.composeecommercestore.data.product.model.Product
import com.qamar.composeecommercestore.util.Result
import kotlinx.coroutines.flow.Flow


interface ProductRemoteDataSource {
    suspend fun getProductByCategoryId(id: Int): com.qamar.composeecommercestore.util.Result<List<Product>>
}
