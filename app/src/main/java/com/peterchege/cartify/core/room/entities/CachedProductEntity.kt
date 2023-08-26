package com.peterchege.cartify.core.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="cached_product")
data class CachedProductEntity(
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