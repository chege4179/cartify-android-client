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
package com.peterchege.cartify.presentation.screens.product_screen

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.domain.models.Product
import com.peterchege.cartify.data.CartRepositoryImpl
import com.peterchege.cartify.domain.mappers.toCartItem
import com.peterchege.cartify.domain.repository.CartRepository
import com.peterchege.cartify.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class ProductScreenViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val savedStateHandle: SavedStateHandle,
    private val cartRepository: CartRepository,
) :ViewModel() {


    val cart = cartRepository.getCart()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    private val _errorMsg = mutableStateOf("")
    val errorMsg:State<String> =_errorMsg

    private val _isError = mutableStateOf(false)
    val isError:State<Boolean> =_isError

    private val _isLoading = mutableStateOf(false)
    val isLoading:State<Boolean> =_isLoading

    private val _product = mutableStateOf<Product?>(null)
    val product : State<Product?> = _product



    init {

        savedStateHandle.get<String>("id")?.let {
            getProductById(it)
        }
    }

    fun addToCart(){
        val newCartItem = _product.value?.toCartItem()
        viewModelScope.launch {
            try {
                cartRepository.insertIntoCart(newCartItem!!)
            }catch (e:IOException){

            }
        }
    }
    fun addToWishList(product: Product, scaffoldState: ScaffoldState){
        viewModelScope.launch {
            try {
                productRepository.addProductToWishList(product = product)
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "${product.name} has been added to your wishlist"
                )
            }catch (e:Exception){

            }
        }

    }
    private fun getProductById(id:String){
        viewModelScope.launch {
            try {

                _isLoading.value = true
                val response = productRepository.getProductById(id)
                _isLoading.value = false
                if (response.success){
                    _product.value = response.product
                }else{
                    _isError.value = true
                    _errorMsg.value = response.msg
                }
            }catch (e:HttpException){
                _isLoading.value = false
                _isError.value = true
                _errorMsg.value = "Server down ... Please try again"
            }catch (e:IOException){
                _isLoading.value = false
                _isError.value = true
                _errorMsg.value = "Please check your internet connection"
            }
        }
    }
}