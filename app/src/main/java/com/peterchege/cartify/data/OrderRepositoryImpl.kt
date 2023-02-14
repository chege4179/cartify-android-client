package com.peterchege.cartify.data

import com.peterchege.cartify.core.api.CartifyApi
import com.peterchege.cartify.core.api.requests.OrderBody
import com.peterchege.cartify.core.api.responses.AllMyOrdersResponse
import com.peterchege.cartify.core.api.responses.OrderResponse
import com.peterchege.cartify.core.room.database.CartifyDatabase
import com.peterchege.cartify.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: CartifyApi,
    private val db: CartifyDatabase
):OrderRepository {
    override suspend fun addOrder(orderBody: OrderBody): OrderResponse {
        return api.addOrder(orderBody = orderBody)
    }
    override suspend fun getAllUserOrders(id:String): AllMyOrdersResponse {
        return api.getMyOrders(id)
    }
}