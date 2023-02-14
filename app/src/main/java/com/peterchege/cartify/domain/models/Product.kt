package com.peterchege.cartify.domain.models

import com.peterchege.cartify.core.room.entities.ProductRoom


data class Product(
    val __v: Int,
    val _id: String,
    val category: String,
    val description: String,
    val images: List<Image>,
    val name: String,
    val offerDuration: String,
    val offerPrice: Int,
    val onOffer: Boolean,
    val price: Int,
    val rating: Int,
    val reviews: List<Any>
)




