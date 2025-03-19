package com.example.lab4.network

import com.example.lab4.model.ProductModel
import com.google.gson.annotations.SerializedName

data class ProductResponse(@SerializedName("products") var products: ArrayList<ProductModel>)