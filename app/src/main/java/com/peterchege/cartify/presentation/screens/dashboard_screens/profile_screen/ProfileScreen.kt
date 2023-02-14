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
package com.peterchege.cartify.presentation.screens.dashboard_screens.profile_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.cartify.presentation.components.CartIconComponent
import com.peterchege.cartify.core.util.Screens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navController: NavController,
    navHostController: NavController,
    viewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val user = viewModel.user.collectAsStateWithLifecycle(initialValue = null)
    val cart = viewModel.cartItems.collectAsStateWithLifecycle(initialValue = emptyList())
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(0.87f),
                            text = "Profile"
                        )
                        CartIconComponent(
                            navController = navHostController,
                            cartCount = cart.value.size
                        )
                    }
                }
            )
        }

    ) {
        if (user.value == null) {
            Text(text = "You have not logged in yet")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    navHostController.navigate(Screens.LOGIN_SCREEN)
                }) {
                    Text(text = "Login")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    navHostController.navigate(Screens.SIGN_UP_SCREEN)
                }) {
                    Text(text = "Sign Up")
                }

            }
        } else {
            user.value.let {
                Column(modifier = Modifier.padding(20.dp)) {
                    if (it != null) {
                        Text(text = it.fullname)
                        Text(text = it.email)


                        Button(
                            onClick = {
                                viewModel.logoutUser()
                            }) {
                            Text(text = "Log Out")

                        }
                    }

                }
            }
        }

    }

}