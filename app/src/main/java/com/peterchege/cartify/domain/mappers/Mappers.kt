/*
 * Copyright 2023 Cartify By Peter Chege
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peterchege.cartify.domain.mappers

import com.peterchege.cartify.core.room.entities.CachedProductEntity
import com.peterchege.cartify.core.room.entities.CartItem
import com.peterchege.cartify.core.room.entities.SavedProductEntity
import com.peterchege.cartify.domain.models.Image
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

fun Product.toSavedProductEntity(): SavedProductEntity {
    return SavedProductEntity(
        name = name,
        _id = _id,
        category = category,
        price = price,
        offerDuration = offerDuration,
        offerPrice = offerPrice,
        description = description,
        onOffer = onOffer,
        rating = rating,
        image = images,
    )
}

fun Product.toCachedProductEntity(): CachedProductEntity {
    return CachedProductEntity(
        name = name,
        _id = _id,
        category = category,
        price = price,
        offerDuration = offerDuration,
        offerPrice = offerPrice,
        description = description,
        onOffer = onOffer,
        rating = rating,
        images = images,
    )
}

fun SavedProductEntity.toExternalModel(): Product {
    return Product(
        _id = _id,
        __v = 0,
        category = category,
        description = description,
        images = image,
        name = name,
        offerDuration = "",
        offerPrice = 0,
        onOffer = false,
        price = price,
        rating = 0,
        reviews = emptyList()

    )
}

fun CachedProductEntity.toExternalModel(): Product {
    return Product(
        _id = _id,
        __v = 0,
        category = category,
        description = description,
        images = images,
        name = name,
        offerDuration = "",
        offerPrice = 0,
        onOffer = false,
        price = price,
        rating = 0,
        reviews = emptyList()

    )
}