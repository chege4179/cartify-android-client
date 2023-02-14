package com.peterchege.cartify.domain.repository

import com.peterchege.cartify.core.api.requests.OrderBody
import com.peterchege.cartify.core.api.responses.AllMyOrdersResponse
import com.peterchege.cartify.core.api.responses.OrderResponse

interface OrderRepository {

    suspend fun addOrder(orderBody: OrderBody):OrderResponse


    suspend fun getAllUserOrders(id:String):AllMyOrdersResponse
}