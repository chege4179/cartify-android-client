package com.peterchege.cartify.ui.screens.dashboard_screens.orders_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.repositories.CartRepository
import com.peterchege.cartify.room.entities.cartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class OrderViewModel @Inject constructor(
    private val cartRepository: CartRepository,

) :ViewModel(){
    private val _cart = mutableStateOf<List<cartItem>>(emptyList())
    val cart : State<List<cartItem>> = _cart

    init {
        getCart()
    }


    private fun getCart(){
        viewModelScope.launch {
            try {
                val cartItems = cartRepository.getCart().collect {
                    _cart.value = it
                }

            }catch (e: IOException){

            }
        }

    }

}