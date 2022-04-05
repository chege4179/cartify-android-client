package com.peterchege.cartify.ui.screens.cart_screen

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.api.requests.OrderBody
import com.peterchege.cartify.models.User
import com.peterchege.cartify.room.entities.cartItem
import com.peterchege.cartify.repositories.CartRepository
import com.peterchege.cartify.repositories.OrderRepository
import com.peterchege.cartify.repositories.UserRepository
import com.peterchege.cartify.util.helperFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val orderRepository: OrderRepository,

):ViewModel() {
    private val currentUser = userRepository.getCurrentUser()

    private val _user = mutableStateOf<User?>(currentUser)
    val user :State<User?> = _user

    private val _cart = mutableStateOf<List<cartItem>>(emptyList())
    val cart :State<List<cartItem>> = _cart

    private val _subtotal = mutableStateOf<Int>(0)
    val subtotal :State<Int> = _subtotal

    private val _isLoading = mutableStateOf(false)
    val isLoading :State<Boolean> = _isLoading

    init {
        getCart()
    }
    fun removeFromCart(id:String){
        viewModelScope.launch {
            try {
                cartRepository.removeFromCart(id)
            }catch (e:IOException){

            }
        }
    }
    fun increaseCartItemQuantity(quantity:Int,id:String){
        val newQuantity = quantity + 1
        viewModelScope.launch {
            try {
                cartRepository.changecartItemQuantity(newQuantity,id)
            }catch (e:IOException){

            }
        }

    }
    fun reduceCartItemQuantity(quantity:Int,id:String){
        if (quantity != 1){
            val newQuantity = quantity - 1
            viewModelScope.launch {
                try {
                    cartRepository.changecartItemQuantity(newQuantity,id)
                }catch (e:IOException){

                }
            }
        }
    }
    private fun getCart(){
        viewModelScope.launch {
            try {
                val cartItems = cartRepository.getCart().collect {
                    _cart.value = it
                }

            }catch (e:IOException){

            }
        }

    }
    fun proceedToOrder(total:Long,scaffoldState: ScaffoldState,context: Context){
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
                if (helperFunctions.hasInternetConnection(context = context)){
                    val response = newOrder?.let {
                        orderRepository.addOrder(it)
                    }
                    _isLoading.value =false
                    if (response != null){
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = response.msg
                        )
                        if(response.success){
                            cartRepository.clearCart()
                        }
                    }else{
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "You have not logged in "
                        )
                    }
                }

            }catch (e:HttpException){
                _isLoading.value =false
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Server down...Please try again later"
                )
            }catch (e:IOException){
                _isLoading.value =false
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Please check your internet connection"
                )
            }
        }

    }

}