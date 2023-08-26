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

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peterchege.cartify.core.util.UiEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun SignUpScreen(
    navigateToLoginScreen:() -> Unit,
    viewModel: SignUpScreenViewModel = hiltViewModel()
){
    val formState by viewModel.uiState.collectAsStateWithLifecycle()

    SignUpScreenContent(
        uiState = formState,
        eventFlow = viewModel.eventFlow,
        navigateToLoginScreen = navigateToLoginScreen,
        onChangeFullName = viewModel::onChangeFullname,
        onChangeEmail = viewModel::onChangeEmail,
        onChangePassword = viewModel::onChangePassword,
        onChangePasswordConfirm = viewModel::onChangeConfirmPassword,
        onChangeAddress = viewModel::onChangeAddress,
        onChangePhoneNumber = viewModel::onChangePhoneNumber,
        onSubmit = { viewModel.signUpUser(navigateToLoginScreen) }
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignUpScreenContent(
    uiState:SignUpFormState,
    eventFlow:SharedFlow<UiEvent>,
    navigateToLoginScreen:() -> Unit,
    onChangeFullName:(String) -> Unit,
    onChangeEmail:(String) -> Unit,
    onChangePassword:(String) -> Unit,
    onChangePasswordConfirm:(String) -> Unit,
    onChangeAddress:(String) -> Unit,
    onChangePhoneNumber:(String) -> Unit,
    onSubmit:() -> Unit,

) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText
                    )
                }
                is UiEvent.Navigate -> {

                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Cartify",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(color = MaterialTheme.colors.primary),
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.fullName,
                    onValueChange = {
                        onChangeFullName(it)

                    },
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.primary
                    ),
                    label = {
                        Text(
                            text = "Full Name",
                            style = TextStyle(color = MaterialTheme.colors.primary),
                        )
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.email,
                    onValueChange = {
                        onChangeEmail(it)

                    },
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.primary
                    ),
                    label = {
                        Text(
                            text = "Email Address",
                            style = TextStyle(color = MaterialTheme.colors.primary),
                        )
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value =uiState.phoneNumber,
                    onValueChange = {
                        onChangePhoneNumber(it)

                    },
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.primary
                    ),
                    label = {
                        Text(
                            text = "Phone Number",
                            style = TextStyle(color = MaterialTheme.colors.primary),
                        )
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.address,
                    onValueChange = {
                        onChangeAddress(it)

                    },
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.primary
                    ),
                    label = {
                        Text(
                            text = "Address",
                            style = TextStyle(color = MaterialTheme.colors.primary)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.password,
                    onValueChange = {
                        onChangePassword(it)
                    },
                    label = {
                        Text(
                            text = "Password",
                            style = TextStyle(color = MaterialTheme.colors.primary),
                        )
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.passwordConfirm,
                    onValueChange = {
                        onChangePasswordConfirm(it)
                    },
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.primary
                    ),
                    label = {
                        Text(
                            text = "Confirm Password",
                            style = TextStyle(color = MaterialTheme.colors.primary),
                        )
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.onBackground
                    ),
                    onClick = {
                        onSubmit()
                    }
                )
                {
                    Text(
                        text = "Sign Up",
                        style = TextStyle(color = MaterialTheme.colors.primary),
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                TextButton(onClick = {
                    navigateToLoginScreen()

                }) {
                    Text(
                        text = "Login",
                        style = TextStyle(color = MaterialTheme.colors.primary),
                    )
                }

            }

        }


    }

}