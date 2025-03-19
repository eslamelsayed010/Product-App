package com.example.lab4.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
data class ProductModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @SerializedName("rating") var rate: Double,
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String,
    @SerializedName("price") var price: Double,
    @SerializedName("brand") var brand: String?,
    @SerializedName("thumbnail") var imageURL: String
)