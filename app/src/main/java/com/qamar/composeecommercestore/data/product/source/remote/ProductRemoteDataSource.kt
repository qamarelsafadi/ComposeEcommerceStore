package com.qamar.composeecommercestore.data.product.source.remote

import com.qamar.composeecommercestore.data.product.model.Product
import com.qamar.composeecommercestore.util.Result


interface ProductRemoteDataSource {
    suspend fun getProductByCategoryId(id: Int): Result<List<Product>>
}
