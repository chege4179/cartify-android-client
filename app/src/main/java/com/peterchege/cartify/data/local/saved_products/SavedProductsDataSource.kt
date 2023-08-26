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
package com.peterchege.cartify.data.local.saved_products

import com.peterchege.cartify.core.di.IoDispatcher
import com.peterchege.cartify.core.room.dao.SavedProductDao
import com.peterchege.cartify.core.room.database.CartifyDatabase
import com.peterchege.cartify.core.room.entities.SavedProductEntity
import com.peterchege.cartify.domain.mappers.toSavedProductEntity
import com.peterchege.cartify.domain.models.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface SavedProductsDataSource {
    suspend fun addProductToWishList(product: Product)

    fun getWishListProducts(): Flow<List<SavedProductEntity>>

    suspend fun deleteWishListProductById(id:String)

    suspend fun deleteAllWishListProducts()
}


class SavedProductsDataSourceImpl @Inject constructor(
    private val db:CartifyDatabase,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
):SavedProductsDataSource{
    override suspend fun addProductToWishList(product: Product){
        return db.savedProductDao.insertSavedProduct(product.toSavedProductEntity())
    }
    override fun getWishListProducts(): Flow<List<SavedProductEntity>> {
        return db.savedProductDao.getAllSavedProducts().flowOn(ioDispatcher)
    }
    override suspend fun deleteWishListProductById(id:String){
        return db.savedProductDao.deleteSavedProductById(id)
    }
    override suspend fun deleteAllWishListProducts(){
        return db.savedProductDao.deleteAllSavedProducts()
    }
}