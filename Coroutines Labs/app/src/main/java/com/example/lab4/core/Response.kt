package com.example.lab4.core

import com.example.lab4.model.ProductModel

sealed class Response {
    data object Loading : Response()
    data class Success(val data: List<ProductModel>): Response()
    data class Failure(val error: Throwable): Response()
}