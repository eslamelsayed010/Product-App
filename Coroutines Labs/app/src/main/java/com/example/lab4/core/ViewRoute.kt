package com.example.lab4.core

import kotlinx.serialization.Serializable

@Serializable
open class ViewRoute {
    @Serializable
    object MainRoute : ViewRoute()

    @Serializable
    object AllProductsRoute : ViewRoute()

    @Serializable
    object FavoriteRoute: ViewRoute()
}