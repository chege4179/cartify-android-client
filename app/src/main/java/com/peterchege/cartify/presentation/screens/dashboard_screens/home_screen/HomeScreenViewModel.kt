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
package com.peterchege.cartify.presentation.screens.dashboard_screens.home_screen

import android.content.Context
import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.core.api.CartifyApi
import com.peterchege.cartify.core.util.Resource
import com.peterchege.cartify.domain.models.Product
import com.peterchege.cartify.core.util.helperFunctions
import com.peterchege.cartify.domain.repository.CartRepository
import com.peterchege.cartify.domain.repository.ProductRepository
import com.peterchege.cartify.domain.use_cases.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val api: CartifyApi,
    private val getAllProductsUseCase: GetProductsUseCase,

    ) : ViewModel() {
    val productsUseCase = getAllProductsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Resource.Loading()
        )
    var searchJob: Job? = null

    val cart = cartRepository.getCart()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )


    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products

    private val _isError = mutableStateOf(false)
    val isError: State<Boolean> = _isError

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _msg = mutableStateOf("")
    val msg: State<String> = _msg

    private val _refreshing = mutableStateOf(false)
    var refreshing: State<Boolean> = _refreshing

    private val _searchTerm = mutableStateOf("")
    val searchTerm: State<String> = _searchTerm


    fun onChangeSearchTerm(query: String, context: Context, scaffoldState: ScaffoldState) {
        _searchTerm.value = query
        if (query.length > 3) {
            _isLoading.value = true
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                try {
                    if (helperFunctions.hasInternetConnection(context = context)) {
                        val response = api.searchProduct(searchTerm = query)
                        _isLoading.value = false
                        if (response.success) {

                            _products.value = response.products
                        }
                    } else {
                        _isLoading.value = false
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "No Internet Connection"
                        )
                    }
                } catch (e: HttpException) {
                    _isLoading.value = false
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Server down....Please try again later"
                    )
                } catch (e: IOException) {
                    _isLoading.value = false
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "An unexpected error occurred"
                    )
                }
            }
        } else if (query.length < 2) {
            viewModelScope.launch {
                delay(500L)
                getProducts()
            }

        }
    }

    fun addToWishList(product: Product, scaffoldState: ScaffoldState) {
        viewModelScope.launch {
            try {
                productRepository.addProductToWishList(product = product)
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "${product.name} added to your wishList"
                )

            } catch (e: IOException) {

            }
        }
    }


    init {
        getProducts()

    }


    fun getProducts() {
        viewModelScope.launch {
            try {
                _refreshing.value = true
                _isLoading.value = true
                val response = productRepository.getAllProducts()
                _refreshing.value = false
                _isLoading.value = false
                _products.value = response.products
                _isError.value = false

            } catch (e: HttpException) {
                _refreshing.value = false
                Log.e("HTTP ERROR", e.localizedMessage)
                _isLoading.value = false
                _isError.value = true
                _msg.value = "Please check your internet connection"

            } catch (e: IOException) {
                _refreshing.value = false
                _isLoading.value = false
                _isError.value = true
                _msg.value = "Server down please try again later"
            }
        }
    }

}