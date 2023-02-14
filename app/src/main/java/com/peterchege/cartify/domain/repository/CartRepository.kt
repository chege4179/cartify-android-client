package com.peterchege.cartify.domain.repository

import com.peterchege.cartify.core.room.entities.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    fun getCart(): Flow<List<CartItem>>

    suspend fun insertIntoCart(cartItem:CartItem)

    suspend fun changeCartItemQuantity(quantity:Int,id:String)

    suspend fun clearCart()

    suspend fun removeFromCart(id:String)




}