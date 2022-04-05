package com.peterchege.cartify.repositories

import com.peterchege.cartify.api.CartifyApi
import com.peterchege.cartify.api.requests.OrderBody
import com.peterchege.cartify.api.responses.AllMyOrdersResponse
import com.peterchege.cartify.api.responses.OrderResponse
import com.peterchege.cartify.room.database.CartifyDatabase
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val api: CartifyApi,
    private val db: CartifyDatabase
) {
    suspend fun addOrder(orderBody: OrderBody):OrderResponse{
        return api.addOrder(orderBody = orderBody)
    }
    suspend fun getAllUserOrders(id:String):AllMyOrdersResponse{
        return api.getMyOrders(id)
    }
}