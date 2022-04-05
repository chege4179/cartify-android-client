package com.peterchege.cartify.ui.screens.dashboard_screens.home_screen

import android.content.Context
import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.api.CartifyApi
import com.peterchege.cartify.models.Product
import com.peterchege.cartify.repositories.CartRepository
import com.peterchege.cartify.repositories.ProductRepository
import com.peterchege.cartify.util.helperFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val api:CartifyApi,

):ViewModel() {
    var searchJob : Job? = null

    private val _cartCount = mutableStateOf(0)
    val cartCount:State<Int> = _cartCount

    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products : State<List<Product>> = _products

    private val _isError = mutableStateOf(false)
    val isError:State<Boolean> =_isError

    private val _isLoading = mutableStateOf(false)
    val isLoading:State<Boolean> =_isLoading

    private val _msg = mutableStateOf("")
    val msg:State<String> = _msg

    private val _refreshing =  mutableStateOf(false)
    var refreshing : State<Boolean> =  _refreshing

    private val _searchTerm = mutableStateOf("")
    val searchTerm:State<String> = _searchTerm


    fun onChangeSearchTerm(query:String,context: Context,scaffoldState: ScaffoldState){
        _searchTerm.value = query
        if (query.length > 3){
            _isLoading.value = true
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                try {
                    if (helperFunctions.hasInternetConnection(context = context)){
                        val response = api.searchProduct(searchTerm = query)
                        _isLoading.value = false
                        if (response.success){

                            _products.value = response.products
                        }
                    }else{
                        _isLoading.value = false
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "No Internet Connection"
                        )
                    }
                }catch (e:HttpException){
                    _isLoading.value = false
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Server down....Please try again later"
                    )
                }catch (e:IOException){
                    _isLoading.value = false
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "An unexpected error occurred"
                    )
                }
            }
        }else if (query.length < 2){
            viewModelScope.launch {
                delay(500L)
                getProducts()
            }

        }
    }
    fun addToWishList(product: Product,scaffoldState: ScaffoldState){
        viewModelScope.launch {
            try {
                productRepository.addProductToWishList(product = product)
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "${product.name} added to your wishList"
                )

            }catch (e:IOException){

            }
        }
    }


    init {
        getProducts()
        getCartCount()
    }
    fun getCartCount(){
        viewModelScope.launch {
            try {
                cartRepository.getCart().collect {
                    _cartCount.value = it.size
                }

            }catch (e:IOException){

            }
        }
    }


    fun getProducts(){
        viewModelScope.launch {
            try {
                _refreshing.value = true
                _isLoading.value = true
                val response = productRepository.getAllProducts()
                _refreshing.value = false
                _isLoading.value = false
                _products.value = response.products
                _isError.value = false

            }catch (e:HttpException){
                _refreshing.value = false
                Log.e("HTTP ERROR",e.localizedMessage)
                _isLoading.value = false
                _isError.value = true
                _msg.value = "Please check your internet connection"

            }catch (e:IOException){
                _refreshing.value = false
                _isLoading.value = false
                _isError.value = true
                _msg.value ="Server down please try again later"
            }
        }
    }

}