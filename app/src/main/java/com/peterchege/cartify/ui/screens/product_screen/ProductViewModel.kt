package com.peterchege.cartify.ui.screens.product_screen

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.models.Product
import com.peterchege.cartify.models.toCartItem
import com.peterchege.cartify.repositories.CartRepository
import com.peterchege.cartify.repositories.ProductRepository
import com.peterchege.cartify.room.entities.ProductRoom
import com.peterchege.cartify.util.helperFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val savedStateHandle: SavedStateHandle,
    private val cartRepository: CartRepository,
) :ViewModel() {

    private val _errorMsg = mutableStateOf("")
    val errorMsg:State<String> =_errorMsg

    private val _isError = mutableStateOf(false)
    val isError:State<Boolean> =_isError

    private val _isLoading = mutableStateOf(false)
    val isLoading:State<Boolean> =_isLoading

    private val _product = mutableStateOf<Product?>(null)
    val product : State<Product?> = _product

    private val _cartCount = mutableStateOf(0)
    val cartCount:State<Int> = _cartCount

    init {
        getCartCount()
        savedStateHandle.get<String>("id")?.let {
            getProductById(it)
        }
    }
    private fun getCartCount(){
        viewModelScope.launch {
            try {
                cartRepository.getCart().collect {
                    _cartCount.value = it.size
                }

            }catch (e:IOException){

            }
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
    fun addToWishList(product:Product,scaffoldState: ScaffoldState){
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