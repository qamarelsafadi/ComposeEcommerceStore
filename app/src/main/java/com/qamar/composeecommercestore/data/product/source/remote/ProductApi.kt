package com.qamar.composeecommercestore.data.product.source.remote

import com.qamar.composeecommercestore.data.product.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET("v1/categories/{id}/products?limit=10&offset=10")
    suspend fun getProduct(
        @Path("id") id: Int
    ): Response<List<Product>>

}