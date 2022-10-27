package com.qamar.composeecommercestore.data.product.source.remote

import com.qamar.composeecommercestore.data.product.model.Product
import com.qamar.composeecommercestore.util.Result
import javax.inject.Inject

class ProductsRemoteDataSource @Inject constructor(
    private val api: ProductApi
) : ProductRemoteDataSource {
    override suspend fun getProductByCategoryId(id: Int): Result<List<Product>> {
        val data: Result<List<Product>> = try {
            val response = api.getProduct(id)
            if (response.isSuccessful) {
                Result.Success(response.body() ?: listOf())
            } else {
                Result.Error(Throwable(message = response.errorBody().toString()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error()
        }
        return data
    }


}
