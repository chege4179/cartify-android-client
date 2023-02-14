package com.peterchege.cartify.core.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.peterchege.cartify.domain.models.Image
import com.peterchege.cartify.domain.models.Product

@Entity(tableName = "product")
data class ProductRoom(
    @PrimaryKey
    val _id: String,
    val category: String,
    val description: String,
    val image: String,
    val name: String,
    val offerDuration: String,
    val offerPrice: Int,
    val onOffer: Boolean,
    val price: Int,
    val rating: Int,
)

fun ProductRoom.toProduct(): Product {
    return Product(
        _id = _id,
        __v = 0,
        category = category,
        description = description,
        images = listOf(Image("id",image)),
        name = name,
        offerDuration = "",
        offerPrice = 0,
        onOffer = false,
        price = price,
        rating = 0,
        reviews = emptyList()

    )
}