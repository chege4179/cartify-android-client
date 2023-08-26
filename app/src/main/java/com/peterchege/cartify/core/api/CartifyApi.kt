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
package com.peterchege.cartify.core.api

import com.peterchege.cartify.core.api.requests.LoginUser
import com.peterchege.cartify.core.api.requests.OrderBody
import com.peterchege.cartify.core.api.requests.SignUpUser
import com.peterchege.cartify.core.api.responses.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface CartifyApi {
    @GET("/product/all")
    suspend fun getAllProducts(): Response<AllProductsResponse>

    @GET("/product/single/{id}")
    suspend fun getProductById(@Path("id") id : String):Response<ProductByIdResponse>

    @GET("/product/search/v2/{searchTerm}")
    suspend fun searchProduct(@Path("searchTerm") searchTerm : String):Response<AllProductsResponse>

    @POST("/user/login")
    suspend fun loginUser(@Body loginUser: LoginUser):Response<LoginResponse>

    @POST("/user/signup")
    suspend fun signUpUser(@Body signUpUser: SignUpUser):Response<SignUpResponse>

    @POST("/order/add")
    suspend fun addOrder(@Body orderBody: OrderBody):Response<OrderResponse>

    @GET("/order/single/{id}")
    suspend fun getMyOrders(@Path("id") id:String):Response<AllMyOrdersResponse>
}