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