package com.example.lab4.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.lab4.core.ProductRepository
import com.example.lab4.database.AppDatabase
import com.example.lab4.database.ProductLocalDataSource
import com.example.lab4.network.ProductRemoteDataSource
import com.example.lab4.network.RetrofitHelper
import com.example.lab4.vm.MyFactory
import com.example.lab4.vm.ProductViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

class ProductActivity : ComponentActivity() {

    private val factory = MyFactory(
        ProductRepository.getInstance(
            ProductRemoteDataSource(RetrofitHelper),
            ProductLocalDataSource(
                AppDatabase
                    .getInstance(this)
                    .productDao()
            )
        )
    )

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        @OptIn(DelicateCoroutinesApi::class)
        val productViewMode = ViewModelProvider(this, factory)[ProductViewModel::class]
        super.onCreate(savedInstanceState)
        setContent {
            AllProductView(productViewMode)
        }
    }
}

