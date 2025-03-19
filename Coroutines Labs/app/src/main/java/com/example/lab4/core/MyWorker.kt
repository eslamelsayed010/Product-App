//package com.example.lab4.core
//
//import android.content.Context
//import android.util.Log
//import androidx.work.CoroutineWorker
//import androidx.work.Data
//import androidx.work.WorkerParameters
//import com.example.lab4.database.AppDatabase
//import com.example.lab4.database.ProductDao
//import com.example.lab4.network.ProductServices
//import com.example.lab4.network.RetrofitHelper
//import com.google.gson.Gson
//
//class ProductWorker(
//    context: Context,
//    params: WorkerParameters,
//) : CoroutineWorker(context, params) {
//    private val productDao: ProductDao = AppDatabase.getInstance(context).productDao()
//    private val retrofitService: ProductServices = RetrofitHelper.retrofitService
//    override suspend fun doWork(): Result {
//
//
//        return try {
//
//            val response = retrofitService.allProducts()
//
//            productDao.insertProducts(response.products)
//
//            Log.i("ProductWorker", "Products successfully fetched and saved.")
//
//            val outputData = Data.Builder()
//                .putString(Constants.DATA, Gson().toJson(response.products))
//                .build()
//
//            Result.success(outputData)
//        } catch (e: Exception) {
//            Log.e("ProductWorker", "Error: ${e.message}")
//            val cachedProducts = productDao.getProducts()
//            if (cachedProducts.isNotEmpty()) {
//                Log.i("ProductWorker", "Loaded cached products from Room DB.")
//                Result.success()
//            } else {
//                Log.e("ProductWorker", "No products available in cache.")
//                val failure = Data
//                    .Builder()
//                    .putString(Constants.FAILURE_REASON, e.message)
//                    .build()
//                Result.failure(failure)
//            }
//        }
//    }
//}
