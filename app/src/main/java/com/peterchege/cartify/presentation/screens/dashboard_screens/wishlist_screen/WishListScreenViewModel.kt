package com.peterchege.cartify.presentation.screens.dashboard_screens.wishlist_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.data.CartRepositoryImpl

import com.peterchege.cartify.core.room.entities.ProductRoom
import com.peterchege.cartify.core.room.entities.CartItem
import com.peterchege.cartify.domain.repository.CartRepository
import com.peterchege.cartify.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListScreenViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
):ViewModel() {

    private val _wishlist = mutableStateOf<List<ProductRoom>>(emptyList())
    val wishlist : State<List<ProductRoom>> = _wishlist

    private val _cartItems = mutableStateOf<List<CartItem>>(emptyList())
    val cartItems: State<List<CartItem>> = _cartItems

    init {
        getCart()
        getWishListProducts()
    }
    private fun getCart(){
        viewModelScope.launch {
            cartRepository.getCart().collect {
                _cartItems.value = it
            }
        }
    }
    fun deleteProductfromRoom(id:String){
        viewModelScope.launch {
            try {
                productRepository.deleteWishListProductById(id)

            }catch (e:Exception){

            }
        }

    }

    private fun getWishListProducts(){
        viewModelScope.launch {
            try {
                productRepository.getWishListProducts().collect {
                    _wishlist.value = it
                }
            }catch (e:Exception){

            }
        }

    }

}