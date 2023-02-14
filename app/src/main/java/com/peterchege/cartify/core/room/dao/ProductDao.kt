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
package com.peterchege.cartify.core.room.dao

import androidx.room.*

import com.peterchege.cartify.core.room.entities.ProductRoom

import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getProducts(): Flow<List<ProductRoom>>

    @Query("SELECT * FROM product WHERE _id = :id")
    suspend fun getProductById(id: String): ProductRoom?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productRoom: ProductRoom)

    @Query("DELETE FROM product WHERE _id = :id")
    suspend fun deleteProductById(id: String)

    @Query("DELETE FROM product")
    suspend fun deleteAllProduct()
}