package com.example.lab4.network

import com.example.lab4.core.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private var retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build()

    var retrofitService: ProductServices = retrofit
        .create(ProductServices::class.java)
}