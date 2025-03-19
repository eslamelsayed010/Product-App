package com.example.lab4.network

import retrofit2.http.GET

interface ProductServices {
    @GET("products")
    suspend fun allProducts(): ProductResponse
}