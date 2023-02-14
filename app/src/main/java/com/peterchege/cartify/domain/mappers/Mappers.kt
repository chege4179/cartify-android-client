package com.peterchege.cartify.domain.mappers

import com.peterchege.cartify.core.room.entities.CartItem
import com.peterchege.cartify.core.room.entities.ProductRoom

import com.peterchege.cartify.domain.models.Product

fun Product.toCartItem(): CartItem {
    return CartItem(
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