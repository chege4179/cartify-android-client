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
package com.peterchege.cartify.presentation.screens.sign_up_screen

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.peterchege.cartify.core.api.CartifyApi
import com.peterchege.cartify.core.api.requests.SignUpUser
import com.peterchege.cartify.core.util.Screens
import com.peterchege.cartify.core.util.helperFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    private val api: CartifyApi

):ViewModel() {
    private var _isLoading = mutableStateOf(false)
    var isLoading : State<Boolean> = _isLoading

    private var _fullNameState = mutableStateOf("")
    var fullNameState: State<String> = _fullNameState

    private var _addressState = mutableStateOf("")
    var addressState: State<String> = _addressState

    private var _emailState = mutableStateOf("")
    var emailState: State<String> = _emailState

    private var _phoneNumberState = mutableStateOf("")
    var phoneNumberState: State<String> = _phoneNumberState

    private var _passwordState = mutableStateOf("")
    var passwordState: State<String> =  _passwordState


    private var _confirmPasswordState = mutableStateOf("")
    var confirmPasswordState: State<String> =  _confirmPasswordState


    fun OnChangeFullname(text:String){
        _fullNameState.value = text
    }
    fun OnChangeAddress(text:String){
        _addressState.value = text
    }
    fun OnChangePassword(text:String){
        _passwordState.value =  text

    }
    fun OnChangeConfirmPassword(text:String){
        _confirmPasswordState.value = text

    }
    fun OnChangeEmail(text:String){
        _emailState.value =  text

    }
    fun OnChangePhoneNumber(text:String){
        _phoneNumberState.value = text

    }
    fun signUpUser(navController: NavController,scaffoldState: ScaffoldState,context: Context){

        viewModelScope.launch {
            _isLoading.value = true
            if(_passwordState.value != _confirmPasswordState.value){
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Passwords do not match"
                )

            }else{
                try {
                    val signUpUser = SignUpUser(
                        fullname = _fullNameState.value,
                        email = _emailState.value,
                        phoneNumber = _phoneNumberState.value,
                        password = _passwordState.value,
                        address = _addressState.value
                    )
                    if(helperFunctions.hasInternetConnection(context)){
                        val response = api.signUpUser(signUpUser = signUpUser)
                        _isLoading.value = false
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = response.msg
                        )
                        if (response.success){
                            navController.navigate(Screens.LOGIN_SCREEN)

                        }
                    }else{
                        _isLoading.value = false
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "No Internet Connection...."
                        )
                    }


                }catch (e:HttpException){
                    _isLoading.value = false
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Server down... Please try again later"
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

}