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

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.cartify.core.api.CartifyApi
import com.peterchege.cartify.core.api.NetworkResult
import com.peterchege.cartify.core.api.requests.LoginUser
import com.peterchege.cartify.data.AuthRepositoryImpl
import com.peterchege.cartify.core.util.Screens
import com.peterchege.cartify.core.util.UiEvent
import com.peterchege.cartify.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

data class FormState(
    val email:String = "",
    val password:String = "",
    val isLoading:Boolean = false
)

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
):ViewModel() {

    private val _uiState = MutableStateFlow(FormState())
    val uiState = _uiState.asStateFlow()


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    fun onChangePassword(text:String){
        _uiState.value = _uiState.value.copy(password = text)


    }
    fun onChangeEmail(text:String){
        _uiState.value = _uiState.value.copy(email = text)


    }
    fun loginUser(){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val loginUser = LoginUser(
                email = _uiState.value.email,
                password = _uiState.value.password
            )
            val response = authRepository.loginUser(loginUser)
            _uiState.value = _uiState.value.copy(isLoading = false)
            when(response){
                is NetworkResult.Success -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar(uiText =response.data.msg))
                    if (response.data.success){
                        _eventFlow.emit(UiEvent.Navigate(route = Screens.DASHBOARD_SCREEN))
                        response.data.user?.let {
                            authRepository.saveUser(it)
                        }
                    }
                }
                is NetworkResult.Exception -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Server down...Please try again later" ))
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "An unexpected error occurred" ))
                }
            }
        }
    }

    fun showNoInternetSnackBar(){
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.ShowSnackbar(uiText ="No Internet found....Please try again later" ))
        }

    }


}