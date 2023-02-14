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
package com.peterchege.cartify.presentation.screens.login_screen

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.peterchege.cartify.core.api.CartifyApi
import com.peterchege.cartify.core.api.requests.LoginUser
import com.peterchege.cartify.data.UserRepositoryImpl
import com.peterchege.cartify.core.util.Screens
import com.peterchege.cartify.core.util.UiEvent
import com.peterchege.cartify.core.util.helperFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val api: CartifyApi,
    private val userRepositoryImpl: UserRepositoryImpl

):ViewModel() {
    private var _isLoading = mutableStateOf(false)
    var isLoading : State<Boolean> = _isLoading

    private var _emailState = mutableStateOf("")
    var emailState: State<String> = _emailState

    private var _passwordState = mutableStateOf("")
    var passwordState: State<String> =  _passwordState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    fun OnChangePassword(text:String){
        _passwordState.value =  text

    }
    fun OnChangeEmail(text:String){
        _emailState.value =  text

    }
    fun loginUser(){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val loginUser = LoginUser(
                    email = _emailState.value,
                    password = _passwordState.value
                )
                val response = api.loginUser(loginUser = loginUser)
                _isLoading.value = false
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText =response.msg ))

                if (response.success){
                    _eventFlow.emit(UiEvent.Navigate(route = Screens.DASHBOARD_SCREEN))
                    response.user?.let {
                        userRepositoryImpl.saveUser(it)
                    }
                }

            }catch (e:HttpException){
                _isLoading.value = false
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Server down...Please try again later" ))


            }catch (e:IOException){
                _isLoading.value = false
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "An unexpected error occurred" ))

            }
        }
    }

    fun showNoInternetSnackBar(){
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.ShowSnackbar(uiText ="No Internet found....Please try again later" ))
        }

    }


}