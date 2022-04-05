package com.peterchege.cartify.models

import com.peterchege.cartify.room.entities.ProductRoom
import com.peterchege.cartify.room.entities.cartItem

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




fun Product.toCartItem(): cartItem {
    return cartItem(
        id = _id,
        name = name,
        price = price,
        imageUrl = images[0].url,
        quantity = 1,

    )

}

fun Product.toProductRoom(): ProductRoom {
    return ProductRoom(
        name = name,
        _id = _id,
        category = category,
        price = price,
        offerDuration = offerDuration,
        offerPrice = offerPrice,
        description = description,
        onOffer = onOffer,
        rating = rating,
        image = images[0]!!.url,
    )
}