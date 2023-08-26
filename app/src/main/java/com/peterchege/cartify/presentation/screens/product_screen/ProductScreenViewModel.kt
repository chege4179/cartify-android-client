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
package com.peterchege.cartify.presentation.screens.product_screen

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.core.room.entities.CartItem
import com.peterchege.cartify.core.util.UiEvent
import com.peterchege.cartify.domain.models.Product
import com.peterchege.cartify.data.CartRepositoryImpl
import com.peterchege.cartify.domain.mappers.toCartItem
import com.peterchege.cartify.domain.repository.CartRepository
import com.peterchege.cartify.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

sealed interface ProductScreenUiState {
    data class Success(val cart:List<CartItem>,val product:Product):ProductScreenUiState

    data class Error(val message:String):ProductScreenUiState

    object Loading:ProductScreenUiState
}

@HiltViewModel
class ProductScreenViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val savedStateHandle: SavedStateHandle,
    private val cartRepository: CartRepository,
) :ViewModel() {

    private val productId = savedStateHandle.get<String>("id") ?: ""

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val uiState = combine(
        cartRepository.getCart(),
        productRepository.getProductById(productId)
    ){ cart, product ->
        if (product == null){
            ProductScreenUiState.Error(message ="Item not found")
        }else{
            ProductScreenUiState.Success(cart = cart,product = product)
        }
    }.onStart { ProductScreenUiState.Loading }
        .catch { ProductScreenUiState.Error(message = "An unexpected error occurred") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ProductScreenUiState.Loading
        )

    fun addToCart(product: Product){
        val newCartItem = product.toCartItem()
        viewModelScope.launch {
            try {
                cartRepository.insertIntoCart(newCartItem)
            }catch (e:IOException){

            }
        }
    }
    fun addToWishList(product: Product,){
        viewModelScope.launch {
            try {
                productRepository.addProductToWishList(product = product)
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "${product.name} has been added to your wishlist"))

            }catch (e:Exception){
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Failed to add to wishlist"))

            }
        }
    }
}