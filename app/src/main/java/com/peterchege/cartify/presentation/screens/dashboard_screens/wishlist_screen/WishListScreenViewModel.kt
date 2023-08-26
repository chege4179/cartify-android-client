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
package com.peterchege.cartify.presentation.screens.dashboard_screens.wishlist_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.core.room.entities.CartItem
import com.peterchege.cartify.domain.models.Product
import com.peterchege.cartify.domain.repository.CartRepository
import com.peterchege.cartify.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface WishListScreenUiState {
    object Loading:WishListScreenUiState

    data class Success(val cart:List<CartItem>,val wishlistItems:List<Product>):WishListScreenUiState

    data class Error(val message:String):WishListScreenUiState
}


@HiltViewModel
class WishListScreenViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
):ViewModel() {

    val uiState = combine(
        cartRepository.getCart(),
        productRepository.getWishListProducts()
    ){ cart,products ->
        WishListScreenUiState.Success(cart = cart,wishlistItems = products)
    }.onStart { WishListScreenUiState.Loading }
        .catch { WishListScreenUiState.Error(message ="An error occurred") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = WishListScreenUiState.Loading
        )

    fun deleteWishListItem(id:String){
        viewModelScope.launch {
            try {
                productRepository.deleteWishListProductById(id)

            }catch (e:Exception){

            }
        }

    }
}