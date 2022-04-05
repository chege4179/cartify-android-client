package com.peterchege.cartify.repositories

import com.peterchege.cartify.room.entities.cartItem
import com.peterchege.cartify.room.database.CartifyDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val db:CartifyDatabase
) {
    fun getCart():Flow<List<cartItem>> {
        return db.cartDao.getCart()
    }
    suspend fun insertIntoCart(cartItem: cartItem){
        return db.cartDao.insertIntoCart(cartItem = cartItem)

    }
    suspend fun changecartItemQuantity(quantity:Int,id:String){
        return db.cartDao.changeCartItemQuantity(quantity = quantity,id = id)
    }
    suspend fun clearCart(){
        return db.cartDao.clearCart()
    }
    suspend fun removeFromCart(id:String){
        return db.cartDao.removeFromCart(id)
    }

}