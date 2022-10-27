package com.qamar.composeecommercestore.data.product.model


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("category")
    val category: Category? = Category(),
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("images")
    val images: List<String?>? = listOf(),
    @SerializedName("price")
    val price: Int? = 0,
    @SerializedName("title")
    val title: String? = ""
)