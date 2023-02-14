package com.peterchege.cartify.data


import com.peterchege.cartify.core.room.database.CartifyDatabase
import com.peterchege.cartify.core.room.entities.CartItem
import com.peterchege.cartify.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val db: CartifyDatabase
) :CartRepository{
    override fun getCart():Flow<List<CartItem>> {
        return db.cartDao.getCart()
    }
    override suspend fun insertIntoCart(cartItem: CartItem){
        return db.cartDao.insertIntoCart(cartItem = cartItem)

    }
    override suspend fun changeCartItemQuantity(quantity:Int, id:String){
        return db.cartDao.changeCartItemQuantity(quantity = quantity,id = id)
    }
    override suspend fun clearCart(){
        return db.cartDao.clearCart()
    }
    override suspend fun removeFromCart(id:String){
        return db.cartDao.removeFromCart(id)
    }

}