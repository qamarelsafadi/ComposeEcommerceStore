package com.qamar.composeecommercestore.data.category


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey @ColumnInfo(name = "entryid") var entryid: String = UUID.randomUUID().toString(),
    @SerializedName("id")
    var categoryId: Int? = 0,
    @SerializedName("image")
    var image: String? = "",
    @SerializedName("name")
    var name: String? = ""
)