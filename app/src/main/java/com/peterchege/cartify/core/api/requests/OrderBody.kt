package com.peterchege.cartify.core.api.requests

import com.peterchege.cartify.core.room.entities.CartItem

data class OrderBody (
    val name:String,
    val email:String,
    val phoneNumber: String,
    val userId:String,
    val address:String,
    val total:Long,
    val products:List<CartItem>,
    )