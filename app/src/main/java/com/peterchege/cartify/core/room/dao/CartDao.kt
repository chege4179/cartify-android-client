/*
 * Copyright 2023 Cartify By Peter Chege
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peterchege.cartify.core.room.dao

import androidx.room.*
import com.peterchege.cartify.core.room.entities.CartItem

import kotlinx.coroutines.flow.Flow


@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoCart(cartItem: CartItem)

    @Query("DELETE  FROM cart where id = :id")
    suspend fun removeFromCart(id:String)

    @Query("DELETE FROM cart")
    suspend fun clearCart()
    @Query("UPDATE cart SET quantity = :quantity WHERE id = :id")
    suspend fun changeCartItemQuantity(quantity:Int,id:String)

    @Query("SELECT * FROM cart")
    fun getCart(): Flow<List<CartItem>>

}