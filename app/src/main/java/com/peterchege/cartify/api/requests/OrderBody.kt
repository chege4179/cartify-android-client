package com.peterchege.cartify.api.requests

import com.peterchege.cartify.room.entities.cartItem

data class OrderBody (
    val name:String,
    val email:String,
    val phoneNumber: String,
    val userId:String,
    val address:String,
    val total:Long,
    val products:List<cartItem>,
    )