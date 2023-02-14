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