package com.example.lab4.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab4.R
import com.example.lab4.core.Response
import com.example.lab4.model.ProductModel
import com.example.lab4.vm.ProductViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun AllProductView(
    productViewMode: ProductViewModel
) {
    productViewMode.getProducts()
    val dataState by (productViewMode.productList).collectAsState()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when(dataState){
                is Response.Loading ->{
                    CircularProgressIndicator()
                }
                is Response.Success -> {
                    var data = (dataState as Response.Success).data
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            Text(
                                "Products",
                                style = TextStyle(fontSize = 30.sp),
                            )
                        }
                        items(data.size) { index ->
                            val data = data.get(index)
                            CustomRow(
                                product = data,
                                color = Color(0xff3add85),
                                text = "Add To Favorite"
                            ) {
                                scope.launch {
                                    productViewMode.addToFav(data!!)
                                    snackBarHostState.showSnackbar(
                                        message = "Added ${data.title} To Favorite.",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        }

                    }
                }
                is Response.Failure -> {
                    Text("There is an error")
                }
            }
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun FavoriteView(
    productViewMode: ProductViewModel
) {
    productViewMode.getLocalData()
    val dataState by (productViewMode.productList).collectAsState()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when(dataState){
                is Response.Loading ->{
                    CircularProgressIndicator()
                }
                is Response.Success -> {
                    var data = (dataState as Response.Success).data
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            Text(
                                "Products",
                                style = TextStyle(fontSize = 30.sp),
                            )
                        }
                        items(data.size) { index ->
                            val data = data.get(index)
                            CustomRow(
                                product = data,
                                color = Color.Red,
                                text = "Remove From Favorite"
                            ) {
                                scope.launch {
                                    productViewMode.deleteFromFav(data)
                                    snackBarHostState.showSnackbar(
                                        message = "Delete ${data.title} From Favorite.",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        }

                    }
                }
                is Response.Failure -> {
                    Text("There is an error")
                }
            }
        }
    }
}

@SuppressLint("InvalidColorHexValue")
@Composable
fun MainView(
    allProductOnClick: () -> Unit,
    favoriteOnClick: () -> Unit,
) {
    val context = LocalOnBackPressedDispatcherOwner.current
    val context2 = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo",
            modifier = Modifier
                .size(350.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        CustomButton(
            color = Color(0xff3add85),
            text = "Get All Products"
        ) {
            context2.startActivity(Intent(context2, ProductActivity::class.java))
            allProductOnClick.invoke()
        }
        Spacer(Modifier.height(20.dp))
        CustomButton(
            color = Color(0xff3add85), text = "Get Favorite Products"
        ) {
            context2.startActivity(Intent(context2, FavoriteActivity::class.java))
            favoriteOnClick.invoke()
        }
        Spacer(Modifier.height(20.dp))
        CustomButton(
            color = Color.Red,
            text = "Exit"
        ) {
            //context?.onBackPressedDispatcher?.onBackPressed()
            context2.startActivity(Intent(context2, SearchActivity::class.java))
        }

    }
}

@Composable
private fun CustomButton(
    color: Color, text: String, onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(color),
        shape = RoundedCornerShape(10.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp,
            focusedElevation = 6.dp,
            hoveredElevation = 6.dp,
            disabledElevation = 0.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(
            text, fontSize = 25.sp
        )
    }
}


@Composable
private fun CustomRow(
    product: ProductModel?,
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Column {
        Row {
            GlideImage(
                imageModel = { product?.imageURL },
                modifier = Modifier
                    .size(150.dp)
                    .padding(10.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.height(150.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center

            ) {
                Text(
                    text = product?.title ?: "", style = TextStyle(
                        fontSize = 22.sp, color = Color.Black
                    )
                )
                Text(
                    text = product?.description ?: "", style = TextStyle(
                        fontSize = 17.sp,
                        color = Color.Gray,
                    ), maxLines = 2, overflow = TextOverflow.Ellipsis
                )
            }
        }
        CustomButton(
            color = color,
            text = text
        ) {
            onClick.invoke()
        }
        HorizontalDivider(
            modifier = Modifier.padding(10.dp),
            thickness = 1.dp,
            color = Color.Gray
        )
    }
}