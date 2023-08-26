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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.core.room.entities.CartItem
import com.peterchege.cartify.core.util.UiEvent
import com.peterchege.cartify.domain.models.Product
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
import java.io.IOException
import javax.inject.Inject

sealed interface HomeScreenUiState {
    object Loading:HomeScreenUiState

    data class Success(val cart:List<CartItem>,val products:List<Product>):HomeScreenUiState

    data class Error(val message:String):HomeScreenUiState
}
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    ) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val uiState = combine(
        productRepository.getAllProducts(),
        cartRepository.getCart()
    ){ products,cart ->
        HomeScreenUiState.Success(cart = cart,products = products)
    }
        .onStart { HomeScreenUiState.Loading }
        .catch { HomeScreenUiState.Error(message = "An error occurred") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = HomeScreenUiState.Loading
        )



    fun addToWishList(product: Product) {
        viewModelScope.launch {
            try {
                productRepository.addProductToWishList(product = product)
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "${product.name} added to your wishList"))


            } catch (e: IOException) {
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Error saving to wishlist"))
            }
        }
    }
}