package com.peterchege.cartify.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cart")
data class cartItem (
    @PrimaryKey
    val id:String,
    val name:String,
    val price:Int,
    val quantity:Int,
    val imageUrl:String,
)