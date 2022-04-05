package com.peterchege.cartify.ui.screens.dashboard_screens.wishlist_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.repositories.CartRepository
import com.peterchege.cartify.repositories.ProductRepository
import com.peterchege.cartify.room.entities.ProductRoom
import com.peterchege.cartify.room.entities.cartItem
import com.peterchege.cartify.ui.screens.product_screen.ProductViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
):ViewModel() {

    private val _wishlist = mutableStateOf<List<ProductRoom>>(emptyList())
    val wishlist : State<List<ProductRoom>> = _wishlist

    private val _cartItems = mutableStateOf<List<cartItem>>(emptyList())
    val cartItems: State<List<cartItem>> = _cartItems

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