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
package com.peterchege.cartify.data.local.cached_products

import com.peterchege.cartify.core.di.IoDispatcher
import com.peterchege.cartify.core.room.database.CartifyDatabase
import com.peterchege.cartify.core.room.entities.CachedProductEntity
import com.peterchege.cartify.domain.mappers.toCachedProductEntity
import com.peterchege.cartify.domain.models.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CachedProductsDataSource {

    fun getAllCachedProducts(): Flow<List<CachedProductEntity>>

    fun getCachedProductById(id:String):Flow<CachedProductEntity?>

    suspend fun deleteAllCachedProducts()

    suspend fun insertCachedProducts(products:List<Product>)
}


class CachedProductDataSourceImpl @Inject constructor(
    private val db:CartifyDatabase,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
):CachedProductsDataSource{

    override suspend fun insertCachedProducts(products: List<Product>) {
        withContext(ioDispatcher){
            db.cachedProductDao.insertCachedProducts(products = products.map { it.toCachedProductEntity() })
        }
    }


    override fun getAllCachedProducts(): Flow<List<CachedProductEntity>> {
        return db.cachedProductDao.getAllCachedProducts().flowOn(ioDispatcher)
    }

    override fun getCachedProductById(id:String): Flow<CachedProductEntity?> {
        return db.cachedProductDao.getCachedProductById(id = id).flowOn(ioDispatcher)
    }

    override suspend fun deleteAllCachedProducts() {
        withContext(ioDispatcher){
            db.cachedProductDao.deleteAllCachedProducts()
        }
    }

}