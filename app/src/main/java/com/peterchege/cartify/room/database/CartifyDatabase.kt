package com.peterchege.cartify.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.peterchege.cartify.room.entities.cartItem
import com.peterchege.cartify.room.dao.CartDao
import com.peterchege.cartify.room.dao.ProductDao
import com.peterchege.cartify.room.entities.ProductRoom

@Database(
    entities = [ProductRoom::class, cartItem::class],
    version = 1
)
abstract class CartifyDatabase: RoomDatabase() {

    abstract val productDao: ProductDao

    abstract val cartDao:CartDao
}