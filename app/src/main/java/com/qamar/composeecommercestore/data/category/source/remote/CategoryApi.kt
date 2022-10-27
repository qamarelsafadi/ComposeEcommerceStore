package com.qamar.composeecommercestore.data.category.source.remote

import com.qamar.composeecommercestore.data.category.model.Category
import retrofit2.Response
import retrofit2.http.GET

interface CategoryApi {

    @GET("v1/categories")
    suspend fun getCategory(): Response<List<Category>>

}