package com.peterchege.cartify.ui.screens.login_screen

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.peterchege.cartify.api.CartifyApi
import com.peterchege.cartify.api.requests.LoginUser
import com.peterchege.cartify.repositories.UserRepository
import com.peterchege.cartify.util.Screens
import com.peterchege.cartify.util.helperFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val api: CartifyApi,
    private val userRepository: UserRepository

):ViewModel() {
    private var _isLoading = mutableStateOf(false)
    var isLoading : State<Boolean> = _isLoading

    private var _emailState = mutableStateOf("")
    var emailState: State<String> = _emailState

    private var _passwordState = mutableStateOf("")
    var passwordState: State<String> =  _passwordState


    fun OnChangePassword(text:String){
        _passwordState.value =  text

    }
    fun OnChangeEmail(text:String){
        _emailState.value =  text

    }
    fun loginUser(
        navController: NavController,
        context: Context,
        scaffoldState: ScaffoldState){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                if (helperFunctions.hasInternetConnection(context = context)){
                    val loginUser = LoginUser(
                        email = _emailState.value,
                        password = _passwordState.value
                    )
                    val response = api.loginUser(loginUser = loginUser)
                    _isLoading.value = false
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = response.msg
                    )
                    if (response.success){
                        navController.navigate(Screens.DASHBOARD_SCREEN)
                        response.user?.let {
                            userRepository.saveUser(it)
                        }
                    }
                }else{
                    _isLoading.value = false
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "No Internet Connection found"
                    )
                }

            }catch (e:HttpException){
                _isLoading.value = false
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Server down...Please try again later"
                )

            }catch (e:IOException){
                _isLoading.value = false
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "An unexpected error occurred"
                )

            }
        }
    }


}