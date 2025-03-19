package com.example.lab4.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lab4.model.ProductModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProducts(products: List<ProductModel>)

    @Query("SELECT * FROM products")
    fun getProducts(): Flow<List<ProductModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProductToFav(product: ProductModel): Long

    @Delete
    suspend fun deleteProductFromFav(product: ProductModel): Int

}
