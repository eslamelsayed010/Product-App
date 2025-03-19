package com.example.lab4.database

import com.example.lab4.model.ProductModel
import kotlinx.coroutines.flow.Flow

class ProductLocalDataSource(private val dao: ProductDao) {
    suspend fun getAllProduct(): Flow<List<ProductModel>> {
        return dao.getProducts()
    }

    suspend fun insertProductToFav(product: ProductModel): Long {
        return dao.insertProductToFav(product)
    }

    suspend fun deleteProduct(product: ProductModel): Int {
        return dao.deleteProductFromFav(product)
    }

//    suspend fun insertProducts(products: List<ProductModel>) {
//        dao.insertProducts(products)
//    }
}