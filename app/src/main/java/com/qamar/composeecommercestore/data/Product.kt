package com.qamar.composeecommercestore.data

import androidx.room.ColumnInfo

data class Product(
    @ColumnInfo(name = "id") var id: Int = 0,
)
