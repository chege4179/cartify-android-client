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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.core.api.requests.OrderBody
import com.peterchege.cartify.core.room.entities.CartItem
import com.peterchege.cartify.core.util.helperFunctions
import com.peterchege.cartify.domain.repository.CartRepository
import com.peterchege.cartify.domain.repository.OrderRepository
import com.peterchege.cartify.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val orderRepository: OrderRepository,

    ) : ViewModel() {
    val user = userRepository.getCurrentUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    private val _cart = mutableStateOf<List<CartItem>>(emptyList())
    val cart: State<List<CartItem>> = _cart

    private val _subtotal = mutableStateOf<Int>(0)
    val subtotal: State<Int> = _subtotal

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        getCart()
    }

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

    private fun getCart() {
        viewModelScope.launch {
            try {
                val cartItems = cartRepository.getCart().collect {
                    _cart.value = it
                }

            } catch (e: IOException) {

            }
        }

    }

    fun proceedToOrder(total: Long, scaffoldState: ScaffoldState, context: Context) {
        val newOrder = user.value?.let {
            OrderBody(
                name = it.fullname,
                email = it.email,
                phoneNumber = it.phoneNumber,
                userId = it._id,
                address = it.address,
                total = total,
                products = cart.value
            )
        }
        viewModelScope.launch {
            try {
                _isLoading.value = true
                if (helperFunctions.hasInternetConnection(context = context)) {
                    val response = newOrder?.let {
                        orderRepository.addOrder(it)
                    }
                    _isLoading.value = false
                    if (response != null) {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = response.msg
                        )
                        if (response.success) {
                            cartRepository.clearCart()
                        }
                    } else {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "You have not logged in "
                        )
                    }
                }

            } catch (e: HttpException) {
                _isLoading.value = false
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Server down...Please try again later"
                )
            } catch (e: IOException) {
                _isLoading.value = false
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Please check your internet connection"
                )
            }
        }

    }

}