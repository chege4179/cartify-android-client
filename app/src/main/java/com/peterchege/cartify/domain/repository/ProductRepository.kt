package com.peterchege.cartify.domain.repository

import com.peterchege.cartify.core.api.responses.AllProductsResponse
import com.peterchege.cartify.core.api.responses.ProductByIdResponse
import com.peterchege.cartify.core.room.entities.ProductRoom
import com.peterchege.cartify.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getAllProducts():AllProductsResponse

    suspend fun getProductById(id:String):ProductByIdResponse


    suspend fun addProductToWishList(product: Product)

    fun getWishListProducts(): Flow<List<ProductRoom>>

    suspend fun deleteWishListProductById(id:String)

    suspend fun deleteAllWishListProducts()
}