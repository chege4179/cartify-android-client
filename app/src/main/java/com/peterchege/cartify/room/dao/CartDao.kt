package com.peterchege.cartify.room.dao

import androidx.room.*
import com.peterchege.cartify.room.entities.cartItem
import kotlinx.coroutines.flow.Flow


@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoCart(cartItem: cartItem)

    @Query("DELETE  FROM cart where id = :id")
    suspend fun removeFromCart(id:String)

    @Query("DELETE FROM cart")
    suspend fun clearCart()
    @Query("UPDATE cart SET quantity = :quantity WHERE id = :id")
    suspend fun changeCartItemQuantity(quantity:Int,id:String)

    @Query("SELECT * FROM cart")
    fun getCart(): Flow<List<cartItem>>

}