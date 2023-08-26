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
import com.peterchege.cartify.core.api.NetworkResult
import com.peterchege.cartify.core.api.requests.SignUpUser
import com.peterchege.cartify.core.util.Screens
import com.peterchege.cartify.core.util.UiEvent
import com.peterchege.cartify.core.util.helperFunctions
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

data class SignUpFormState(
    val isLoading:Boolean = false,
    val fullName:String = "",
    val address:String = "",
    val email:String = "",
    val phoneNumber:String = "",
    val password:String = "",
    val passwordConfirm:String = "",
)

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpFormState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onChangeFullname(text: String) {
        _uiState.value = _uiState.value.copy(fullName = text)
    }

    fun onChangeAddress(text: String) {
        _uiState.value = _uiState.value.copy(address = text)
    }

    fun onChangePassword(text: String) {
        _uiState.value = _uiState.value.copy(password = text)

    }

    fun onChangeConfirmPassword(text: String) {
        _uiState.value = _uiState.value.copy(passwordConfirm = text)

    }

    fun onChangeEmail(text: String) {
        _uiState.value = _uiState.value.copy(email = text)

    }

    fun onChangePhoneNumber(text: String) {
        _uiState.value = _uiState.value.copy(phoneNumber = text)

    }

    fun signUpUser(navigateToLoginScreen:() -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            if (_uiState.value.password != _uiState.value.password) {
                _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Passwords don't match"))

            } else {
                val signUpUser = SignUpUser(
                    fullname = _uiState.value.fullName,
                    email = _uiState.value.email,
                    phoneNumber = _uiState.value.phoneNumber,
                    password = _uiState.value.password,
                    address = _uiState.value.address
                )
                val response = authRepository.signUpUser(signUpUser = signUpUser)
                when(response){
                    is NetworkResult.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        _eventFlow.emit(UiEvent.ShowSnackbar(uiText = response.data.msg))
                        if (response.data.success) {
                            navigateToLoginScreen()
                        }
                    }
                    is NetworkResult.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                uiText = "Server down... Please try again later"
                            )
                        )
                    }
                    is NetworkResult.Exception -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                uiText =  "An unexpected error occurred"
                            )
                        )
                    }
                }
            }
        }
    }
}