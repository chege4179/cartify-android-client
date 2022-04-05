package com.peterchege.cartify.api.responses

import com.peterchege.cartify.models.Order

data class AllMyOrdersResponse (
    val msg:String,
    val success:Boolean,
    val orders:List<Order>?
    )