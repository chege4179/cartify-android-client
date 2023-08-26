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

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peterchege.cartify.core.util.UiEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    navigateToSignUpScreen:() -> Unit,
    navigateToDashboardScreen:() -> Unit,
    viewModel: LoginScreenViewModel = hiltViewModel()
){
    val formState by viewModel.uiState.collectAsStateWithLifecycle()
    LoginScreenContent(
        formState = formState,
        onChangeEmail = viewModel::onChangeEmail,
        onChangePassword = viewModel::onChangePassword,
        onSubmit = { viewModel.loginUser(navigateToDashboardScreen) },
        eventFlow = viewModel.eventFlow,
        navigateToSignUpScreen = navigateToSignUpScreen
    )
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalComposeUiApi
@Composable
fun LoginScreenContent(
    formState: FormState,
    onChangeEmail:(String)-> Unit,
    onChangePassword:(String) -> Unit,
    onSubmit:() -> Unit,
    eventFlow:SharedFlow<UiEvent>,
    navigateToSignUpScreen:() -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
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
            .padding(20.dp),

        ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (formState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = formState.email,
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
                    value = formState.password,
                    onValueChange = {
                        onChangePassword(it)

                    },
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.primary
                    ),
                    label = {
                        Text(
                            text = "Password",
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
                        keyboardController?.hide()
                        onSubmit()

                    }
                )
                {
                    Text(
                        text = "Login",
                        style = TextStyle(color = MaterialTheme.colors.primary),
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                TextButton(onClick = {
                    navigateToSignUpScreen()

                }) {
                    Text(
                        text = "Don't have an account yet...Sign Up",
                        style = TextStyle(color = MaterialTheme.colors.primary),
                    )
                }
            }
        }
    }
}