package com.qamar.composeecommercestore.data.product

import com.qamar.composeecommercestore.data.product.model.Product
import kotlinx.coroutines.flow.Flow
import  com.qamar.composeecommercestore.util.Result

interface ProductRepository {

    fun getProductByCategoryId(id: Int): Flow<Result<List<Product>>>

}