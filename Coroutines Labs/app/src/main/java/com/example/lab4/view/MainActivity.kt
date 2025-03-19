package com.example.lab4.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab4.core.ProductRepository
import com.example.lab4.core.ViewRoute
import com.example.lab4.database.AppDatabase
import com.example.lab4.database.ProductLocalDataSource
import com.example.lab4.network.ProductRemoteDataSource
import com.example.lab4.network.RetrofitHelper
import com.example.lab4.vm.MyFactory
import com.example.lab4.vm.ProductViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    private lateinit var factory: MyFactory

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        factory = MyFactory(
            ProductRepository.getInstance(
                ProductRemoteDataSource(RetrofitHelper),
                ProductLocalDataSource(
                    AppDatabase
                        .getInstance(this)
                        .productDao()
                )
            )
        )

        val productViewMode = ViewModelProvider(this, factory)[ProductViewModel::class]
        setContent {
            navHostController = rememberNavController()
            //SetupNavHost(productViewMode)
            MainView({}) { }
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    @Composable
    fun SetupNavHost(productViewMode: ProductViewModel) {
        NavHost(
            navHostController,
            ViewRoute.MainRoute,
            Modifier.fillMaxSize()
        ) {
            composable<ViewRoute.MainRoute> {
                MainView(
                    allProductOnClick = { navHostController.navigate(ViewRoute.AllProductsRoute) },
                    favoriteOnClick = { navHostController.navigate(ViewRoute.FavoriteRoute) }
                )
            }
            composable<ViewRoute.AllProductsRoute> {
                AllProductView(productViewMode)
            }
            composable<ViewRoute.FavoriteRoute> {
                FavoriteView(productViewMode)
            }
        }
    }
}