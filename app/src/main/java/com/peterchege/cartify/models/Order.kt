package com.peterchege.cartify.models

import com.peterchege.cartify.room.entities.cartItem

data class Order (
    val _id:String,
    val name:String,
    val email:String,
    val phoneNumber: String,
    val userId:String,
    val address:String,
    val total:Long,
    val products:List<cartItem>,
    val isPaid:Boolean,
    val isDelivered:Boolean,
)