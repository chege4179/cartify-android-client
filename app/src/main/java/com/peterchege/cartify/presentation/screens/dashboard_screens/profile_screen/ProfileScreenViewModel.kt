package com.peterchege.cartify.presentation.screens.dashboard_screens.profile_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.core.room.entities.CartItem
import com.peterchege.cartify.domain.repository.CartRepository
import com.peterchege.cartify.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val cartRepository: CartRepository,

    ) : ViewModel() {

    val cartItems = cartRepository.getCart()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )
    val user = userRepository.getCurrentUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )



    init {

    }



    fun logoutUser() {
        viewModelScope.launch {
            userRepository.logoutUser()
        }
    }


}