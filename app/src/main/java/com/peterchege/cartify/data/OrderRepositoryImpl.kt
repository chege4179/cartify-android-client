/*
 * Copyright 2023 Cartify By Peter Chege
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peterchege.cartify.data

import com.peterchege.cartify.core.api.CartifyApi
import com.peterchege.cartify.core.api.NetworkResult
import com.peterchege.cartify.core.api.requests.OrderBody
import com.peterchege.cartify.core.api.responses.AllMyOrdersResponse
import com.peterchege.cartify.core.api.responses.OrderResponse
import com.peterchege.cartify.core.api.safeApiCall
import com.peterchege.cartify.core.di.IoDispatcher
import com.peterchege.cartify.core.room.database.CartifyDatabase
import com.peterchege.cartify.domain.repository.OrderRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: CartifyApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
):OrderRepository {
    override suspend fun addOrder(orderBody: OrderBody): NetworkResult<OrderResponse> {
        return withContext(ioDispatcher){
            safeApiCall { api.addOrder(orderBody = orderBody) }
        }
    }
    override suspend fun getAllUserOrders(id:String): NetworkResult<AllMyOrdersResponse> {
        return withContext(ioDispatcher){
            safeApiCall { api.getMyOrders(id) }
        }
    }
}