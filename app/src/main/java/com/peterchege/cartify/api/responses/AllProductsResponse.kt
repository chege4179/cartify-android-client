package com.peterchege.cartify.api.responses

import com.peterchege.cartify.models.Product

data class AllProductsResponse(
    val msg:String,
    val success:Boolean,
    val products:List<Product>
)