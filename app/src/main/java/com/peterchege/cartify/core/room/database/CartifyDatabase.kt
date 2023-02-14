package com.peterchege.cartify.core.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.peterchege.cartify.core.room.dao.CartDao
import com.peterchege.cartify.core.room.dao.ProductDao
import com.peterchege.cartify.core.room.entities.CartItem
import com.peterchege.cartify.core.room.entities.ProductRoom

@Database(
    entities = [ProductRoom::class, CartItem::class],
    version = 1
)
abstract class CartifyDatabase: RoomDatabase() {

    abstract val productDao: ProductDao

    abstract val cartDao: CartDao
}