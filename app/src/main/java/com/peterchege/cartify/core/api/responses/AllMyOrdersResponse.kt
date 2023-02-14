package com.peterchege.cartify.core.api.responses

import com.peterchege.cartify.domain.models.Order

data class AllMyOrdersResponse (
    val msg:String,
    val success:Boolean,
    val orders:List<Order>?
    )