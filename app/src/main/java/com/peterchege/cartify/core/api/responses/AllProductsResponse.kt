package com.peterchege.cartify.core.api.responses

import com.peterchege.cartify.domain.models.Product

data class AllProductsResponse(
    val msg:String,
    val success:Boolean,
    val products:List<Product>
)