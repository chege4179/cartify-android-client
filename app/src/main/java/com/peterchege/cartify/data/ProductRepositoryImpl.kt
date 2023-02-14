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
package com.peterchege.cartify.data

import com.peterchege.cartify.core.api.CartifyApi
import com.peterchege.cartify.core.api.responses.AllProductsResponse
import com.peterchege.cartify.core.api.responses.ProductByIdResponse
import com.peterchege.cartify.domain.models.Product
import com.peterchege.cartify.core.room.database.CartifyDatabase
import com.peterchege.cartify.core.room.entities.ProductRoom
import com.peterchege.cartify.domain.mappers.toProductRoom
import com.peterchege.cartify.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: CartifyApi,
    private val db: CartifyDatabase
):ProductRepository {
    override suspend fun getAllProducts(): AllProductsResponse {
        return api.getAllProducts()
    }
    override suspend fun getProductById(id:String): ProductByIdResponse {
        return api.getProductById(id)

    }
    override suspend fun addProductToWishList(product: Product){
        return db.productDao.insertProduct(productRoom = product.toProductRoom())
    }
    override fun getWishListProducts(): Flow<List<ProductRoom>> {
        return db.productDao.getProducts()
    }
    override suspend fun deleteWishListProductById(id:String){
        return db.productDao.deleteProductById(id)
    }
    override suspend fun deleteAllWishListProducts(){
        return db.productDao.deleteAllProduct()
    }


}