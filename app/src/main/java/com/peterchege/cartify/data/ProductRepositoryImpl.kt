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

import com.peterchege.cartify.core.api.NetworkResult
import com.peterchege.cartify.core.api.responses.AllProductsResponse
import com.peterchege.cartify.data.local.cached_products.CachedProductsDataSource
import com.peterchege.cartify.data.local.saved_products.SavedProductsDataSource
import com.peterchege.cartify.data.remote.RemoteProductsDataSource
import com.peterchege.cartify.domain.mappers.toExternalModel
import com.peterchege.cartify.domain.models.Product
import com.peterchege.cartify.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val savedProductsDataSource: SavedProductsDataSource,
    private val cachedProductsDataSource: CachedProductsDataSource,
    private val remoteProductsDataSource: RemoteProductsDataSource,
):ProductRepository {
    override  fun getAllProducts():Flow<List<Product>>{
        return cachedProductsDataSource.getAllCachedProducts().map { it.map { it.toExternalModel() } }
    }

    override suspend fun searchProducts(query:String): NetworkResult<AllProductsResponse> {
        return remoteProductsDataSource.searchProduct(query = query)
    }
    override fun getProductById(id:String): Flow<Product?> = flow {
        val cachedProduct = cachedProductsDataSource.getCachedProductById(id)
            .map { it?.toExternalModel() }.first()
        if (cachedProduct != null){
            emit(cachedProduct)
        }else{
            val remoteProductResponse = remoteProductsDataSource.getProductById(id)
            when(remoteProductResponse){
                is NetworkResult.Success -> {
                    emit(remoteProductResponse.data.product)
                }
                is NetworkResult.Error -> {
                    emit(null)
                }
                is NetworkResult.Exception -> {
                    emit(null)
                }
            }
        }
    }
    override suspend fun addProductToWishList(product: Product){
        return savedProductsDataSource.addProductToWishList(product)
    }
    override fun getWishListProducts(): Flow<List<Product>> {
        return savedProductsDataSource.getWishListProducts().map { it.map { it.toExternalModel() } }
    }
    override suspend fun deleteWishListProductById(id:String){
        return savedProductsDataSource.deleteWishListProductById(id)
    }
    override suspend fun deleteAllWishListProducts(){
        return savedProductsDataSource.deleteAllWishListProducts()
    }


}