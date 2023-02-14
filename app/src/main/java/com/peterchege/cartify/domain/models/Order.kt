package com.peterchege.cartify.domain.models

import com.peterchege.cartify.core.room.entities.CartItem

data class Order (
    val _id:String,
    val name:String,
    val email:String,
    val phoneNumber: String,
    val userId:String,
    val address:String,
    val total:Long,
    val products:List<CartItem>,
    val isPaid:Boolean,
    val isDelivered:Boolean,
)