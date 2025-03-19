package com.example.lab4.network

import com.example.lab4.model.ProductModel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRemoteDataSource(private val service: RetrofitHelper) {
    fun getAllProduct(): Flow<List<ProductModel>> = flow {
        emit(service.retrofitService.allProducts().products)
    }
}
