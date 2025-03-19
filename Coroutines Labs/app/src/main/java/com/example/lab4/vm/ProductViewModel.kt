package com.example.lab4.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lab4.core.ProductRepository
import com.example.lab4.core.Response
import com.example.lab4.model.ProductModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@DelicateCoroutinesApi
class ProductViewModel(private val repo: ProductRepository) : ViewModel() {

    private var mutList = MutableStateFlow<Response>(Response.Loading)
    val productList = mutList.asStateFlow()

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            try {
                val products = repo.getNetworkAllProduct()
                products.catch { ex ->
                    mutList.value = Response.Failure(ex)
                    _toastEvent.emit("Error From API: ${ex.message}")
                }
                    .collect {
                        mutList.value = Response.Success(it)
                    }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
                mutList.value = Response.Failure(e)
                _toastEvent.emit("Error API: ${e.message}")
            }
        }
    }

    fun addToFav(productModel: ProductModel) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repo.addProductToFav(productModel)
                }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
        }
    }

    fun deleteFromFav(productModel: ProductModel) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repo.deleteProduct(productModel)
                }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
        }
    }

    fun getLocalData() {
        viewModelScope.launch {
            try {
                val products = repo.getLocalAllProduct()
                products.catch { ex ->
                    mutList.value = Response.Failure(ex)
                    _toastEvent.emit("Error From DAO: ${ex.message}")

                }
                    .collect {
                        mutList.value = Response.Success(it)
                    }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
                mutList.value = Response.Failure(e)
                _toastEvent.emit("Error DAO: ${e.message}")
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
@OptIn(DelicateCoroutinesApi::class)
class MyFactory(private val repo: ProductRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(repo) as T
    }
}