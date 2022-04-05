package com.peterchege.cartify.ui.screens.dashboard_screens.profile_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.models.User
import com.peterchege.cartify.repositories.CartRepository
import com.peterchege.cartify.repositories.UserRepository
import com.peterchege.cartify.room.entities.cartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.internal.processedrootsentinel.codegen._com_peterchege_cartify_CartifyApp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val cartRepository: CartRepository,

):ViewModel() {
    val currentUser = userRepository.getCurrentUser()

    private val _user = mutableStateOf<User?>(currentUser)
    val user :State<User?> = _user


    private val _cartItems = mutableStateOf<List<cartItem>>(emptyList())
    val cartItems: State<List<cartItem>> = _cartItems

    init {
        getCart()
    }
    private fun getCart(){
        viewModelScope.launch {
            cartRepository.getCart().collect {
                _cartItems.value = it
            }
        }
    }

    fun logoutUser(){
        userRepository.logoutUser()
        _user.value = null


    }


}