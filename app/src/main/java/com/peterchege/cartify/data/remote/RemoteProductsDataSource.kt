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
package com.peterchege.cartify.data.remote

import com.peterchege.cartify.core.api.CartifyApi
import com.peterchege.cartify.core.api.NetworkResult
import com.peterchege.cartify.core.api.responses.AllProductsResponse
import com.peterchege.cartify.core.api.responses.ProductByIdResponse
import com.peterchege.cartify.core.api.safeApiCall
import com.peterchege.cartify.core.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RemoteProductsDataSource {

    suspend fun getAllProducts(): NetworkResult<AllProductsResponse>

    suspend fun searchProduct(query:String):NetworkResult<AllProductsResponse>


    suspend fun getProductById(id:String):NetworkResult<ProductByIdResponse>

}


class RemoteProductsDataSourceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val api:CartifyApi,
):RemoteProductsDataSource{
    override suspend fun getAllProducts(): NetworkResult<AllProductsResponse> {
        return withContext(ioDispatcher){
            safeApiCall { api.getAllProducts() }
        }
    }

    override suspend fun searchProduct(query: String): NetworkResult<AllProductsResponse> {
        return withContext(ioDispatcher){
            safeApiCall { api.searchProduct(query) }
        }
    }

    override suspend fun getProductById(id: String): NetworkResult<ProductByIdResponse> {
        return withContext(ioDispatcher){
            safeApiCall { api.getProductById(id) }
        }
    }


}


