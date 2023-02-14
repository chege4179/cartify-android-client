package com.peterchege.cartify.core.api.responses

import com.peterchege.cartify.domain.models.Product

data class ProductByIdResponse (
        val msg:String,
        val success:Boolean,
        val product: Product,
)