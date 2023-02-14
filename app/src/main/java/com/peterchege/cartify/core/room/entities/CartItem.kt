package com.peterchege.cartify.core.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cart")
data class CartItem (
    @PrimaryKey
    val id:String,
    val name:String,
    val price:Int,
    val quantity:Int,
    val imageUrl:String,
)