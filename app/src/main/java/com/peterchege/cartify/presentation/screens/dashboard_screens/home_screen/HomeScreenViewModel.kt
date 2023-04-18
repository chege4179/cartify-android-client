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
import com.peterchege.cartify.core.util.helperFunctions
import com.peterchege.cartify.domain.models.Product
import com.peterchege.cartify.domain.repository.CartRepository
import com.peterchege.cartify.domain.repository.ProductRepository
import com.peterchege.cartify.domain.state.Products
import com.peterchege.cartify.domain.state.ProductsUiState
import com.peterchege.cartify.domain.use_cases.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
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

    private val _uiState =
        MutableStateFlow<ProductsUiState>(ProductsUiState.Idle(message = "Loading"))
    val uiState = _uiState.asStateFlow()


    val cart = cartRepository.getCart()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )







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


    private fun getProducts() {
        viewModelScope.launch {
            _uiState.value = ProductsUiState.Loading(message = "Loading")
            try {
                val response = productRepository.getAllProducts()
                _uiState.value = ProductsUiState.Success(data = Products(products = response.products))

            } catch (e: HttpException) {
                _uiState.value = ProductsUiState.Error(errorMessage = "Please check your internet connection")


            } catch (e: IOException) {
                _uiState.value = ProductsUiState.Error(errorMessage = "Server down please try again later")
            }
        }
    }

}