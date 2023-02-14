package com.peterchege.cartify.core.api

import com.peterchege.cartify.core.api.requests.LoginUser
import com.peterchege.cartify.core.api.requests.OrderBody
import com.peterchege.cartify.core.api.requests.SignUpUser
import com.peterchege.cartify.core.api.responses.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface CartifyApi {
    @GET("/product/all")
    suspend fun getAllProducts(): AllProductsResponse

    @GET("/product/single/{id}")
    suspend fun getProductById(@Path("id") id : String): ProductByIdResponse

    @GET("/product/search/v2/{searchTerm}")
    suspend fun searchProduct(@Path("searchTerm") searchTerm : String): AllProductsResponse

    @POST("/user/login")
    suspend fun loginUser(@Body loginUser: LoginUser): LoginResponse

    @POST("/user/signup")
    suspend fun signUpUser(@Body signUpUser: SignUpUser): SignUpResponse

    @POST("/order/add")
    suspend fun addOrder(@Body orderBody: OrderBody): OrderResponse

    @GET("/order/single/{id}")
    suspend fun getMyOrders(@Path("id") id:String): AllMyOrdersResponse
}