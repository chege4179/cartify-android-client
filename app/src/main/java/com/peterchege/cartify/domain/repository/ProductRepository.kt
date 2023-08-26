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
package com.peterchege.cartify.domain.repository

import com.peterchege.cartify.core.api.NetworkResult
import com.peterchege.cartify.core.api.responses.AllProductsResponse
import com.peterchege.cartify.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getAllProducts(): Flow<List<Product>>

    suspend fun searchProducts(query:String): NetworkResult<AllProductsResponse>

    fun getProductById(id: String): Flow<Product?>


    suspend fun addProductToWishList(product: Product)

    fun getWishListProducts(): Flow<List<Product>>

    suspend fun deleteWishListProductById(id: String)

    suspend fun deleteAllWishListProducts()
}