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


