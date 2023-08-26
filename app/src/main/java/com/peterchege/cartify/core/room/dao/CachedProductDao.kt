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

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peterchege.cartify.core.room.entities.CachedProductEntity
import com.peterchege.cartify.core.room.entities.SavedProductEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CachedProductDao {

    @Query("SELECT * FROM cached_product")
    fun getAllCachedProducts(): Flow<List<CachedProductEntity>>

    @Query("SELECT * FROM cached_product WHERE _id = :id")
    fun getCachedProductById(id: String):Flow<CachedProductEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedProduct(product: CachedProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedProducts(products: List<CachedProductEntity>)

    @Query("DELETE FROM cached_product WHERE _id = :id")
    suspend fun deleteCachedProductById(id: String)

    @Query("DELETE FROM saved_product")
    suspend fun deleteAllCachedProducts()
}