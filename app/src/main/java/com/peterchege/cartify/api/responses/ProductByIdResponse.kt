package com.peterchege.cartify.api.responses

import com.peterchege.cartify.models.Product

data class ProductByIdResponse (
        val msg:String,
        val success:Boolean,
        val product: Product,
)