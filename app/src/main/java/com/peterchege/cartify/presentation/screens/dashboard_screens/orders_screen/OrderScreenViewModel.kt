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
package com.peterchege.cartify.presentation.screens.dashboard_screens.orders_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.core.room.entities.CartItem
import com.peterchege.cartify.data.CartRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class OrderViewModel @Inject constructor(
    private val cartRepositoryImpl: CartRepositoryImpl,

    ) :ViewModel(){
    private val _cart = mutableStateOf<List<CartItem>>(emptyList())
    val cart : State<List<CartItem>> = _cart

    init {
        getCart()
    }


    private fun getCart(){
        viewModelScope.launch {
            try {
                val cartItems = cartRepositoryImpl.getCart().collect {
                    _cart.value = it
                }

            }catch (e: IOException){

            }
        }

    }

}