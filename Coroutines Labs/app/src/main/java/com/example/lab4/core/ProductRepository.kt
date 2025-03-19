package com.example.lab4.core

import com.example.lab4.database.ProductLocalDataSource
import com.example.lab4.model.ProductModel
import com.example.lab4.network.ProductRemoteDataSource
import kotlinx.coroutines.flow.Flow

class ProductRepository private constructor(
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductLocalDataSource
) {
    suspend fun getNetworkAllProduct(): Flow<List<ProductModel>> {
        return remoteDataSource.getAllProduct()
    }

    suspend fun getLocalAllProduct(): Flow<List<ProductModel>> {
        return localDataSource.getAllProduct()
    }



    suspend fun addProductToFav(productModel: ProductModel): Long {
        return localDataSource.insertProductToFav(productModel)
    }

//    suspend fun insertProducts(products: List<ProductModel>) {
//        return localDataSource.insertProducts(products)
//    }

    suspend fun deleteProduct(productModel: ProductModel): Int {
        return localDataSource.deleteProduct(productModel)
    }

    companion object {
        private var INSTANCE: ProductRepository? = null

        fun getInstance(
            remoteDataSource: ProductRemoteDataSource,
            localDataSource: ProductLocalDataSource
        ): ProductRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ProductRepository(remoteDataSource, localDataSource).also {
                    INSTANCE = it
                }
            }
        }
    }
}
