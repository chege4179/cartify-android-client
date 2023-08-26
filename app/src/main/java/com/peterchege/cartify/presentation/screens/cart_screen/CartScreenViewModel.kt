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
package com.peterchege.cartify.presentation.screens.cart_screen

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.core.api.NetworkResult
import com.peterchege.cartify.core.api.requests.OrderBody
import com.peterchege.cartify.core.room.entities.CartItem
import com.peterchege.cartify.core.util.UiEvent
import com.peterchege.cartify.core.util.helperFunctions
import com.peterchege.cartify.domain.models.User
import com.peterchege.cartify.domain.repository.CartRepository
import com.peterchege.cartify.domain.repository.OrderRepository
import com.peterchege.cartify.domain.repository.AuthRepository
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
import javax.inject.Inject

sealed interface CartScreenUiState {
    data class Success(val user: User?, val cart:List<CartItem>):CartScreenUiState

    object Loading:CartScreenUiState

    data class Error(val message:String):CartScreenUiState

}

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val authRepository: AuthRepository,
    private val orderRepository: OrderRepository,

    ) : ViewModel() {


    val uiState = combine(
        authRepository.getCurrentUser(),
        cartRepository.getCart()
    ){ authUser,cart ->
        CartScreenUiState.Success(user = authUser,cart = cart)
    }
        .onStart { CartScreenUiState.Loading }
        .catch { CartScreenUiState.Error(message = "An unexpected error occurred") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = CartScreenUiState.Loading
        )

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun removeFromCart(id: String) {
        viewModelScope.launch {
            try {
                cartRepository.removeFromCart(id)
            } catch (e: IOException) {

            }
        }
    }

    fun increaseCartItemQuantity(quantity: Int, id: String) {
        val newQuantity = quantity + 1
        viewModelScope.launch {
            try {
                cartRepository.changeCartItemQuantity(quantity = newQuantity, id = id)
            } catch (e: IOException) {

            }
        }

    }

    fun reduceCartItemQuantity(quantity: Int, id: String) {
        if (quantity != 1) {
            val newQuantity = quantity - 1
            viewModelScope.launch {
                try {
                    cartRepository.changeCartItemQuantity(quantity = newQuantity, id = id)
                } catch (e: IOException) {

                }
            }
        }
    }



    fun proceedToOrder(total: Long, user: User?,cart: List<CartItem>) {
        val newOrder = user?.let {
            OrderBody(
                name = it.fullname,
                email = it.email,
                phoneNumber = it.phoneNumber,
                userId = it._id,
                address = it.address,
                total = total,
                products = cart
            )
        }
        viewModelScope.launch {
            val response = newOrder?.let {
                orderRepository.addOrder(it)
            }
            _isLoading.value = false
            if (response != null) {
                when(response){
                    is NetworkResult.Success -> {
                        _eventFlow.emit(UiEvent.ShowSnackbar(uiText =response.data.msg ))

                        if (response.data.success) {
                            cartRepository.clearCart()
                        }
                    }
                    is NetworkResult.Error -> {
                        _isLoading.value = false
                        _eventFlow.emit(UiEvent.ShowSnackbar(uiText ="Server down...Please try again later" ))

                    }
                    is NetworkResult.Exception -> {
                        _isLoading.value = false
                        _eventFlow.emit(UiEvent.ShowSnackbar(uiText ="Please check your internet connection" ))

                    }
                }

            } else {
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText ="You have not logged in " ))

            }
        }
    }

}