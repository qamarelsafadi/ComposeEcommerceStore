package com.qamar.composeecommercestore.data.product

import com.qamar.composeecommercestore.data.product.model.Product
import kotlinx.coroutines.flow.Flow
import  com.qamar.composeecommercestore.util.Result

interface ProductRepository {

   suspend fun getProductByCategoryId(id: Int): Result<List<Product>>

}