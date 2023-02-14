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
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.cartify.core.util.Constants
import com.peterchege.cartify.presentation.components.CartIconComponent
import com.peterchege.cartify.core.util.Screens
import com.peterchege.cartify.presentation.components.NoLoggedInUserScreenComponent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navController: NavController,
    navHostController: NavController,
    viewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val themeState = viewModel.theme.collectAsStateWithLifecycle()
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
            NoLoggedInUserScreenComponent(navHostController = navHostController)
        }else if(user.value!!._id ==""){
            NoLoggedInUserScreenComponent(navHostController = navHostController)
        }  else {
            user.value.let { user ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    if (user != null) {
                        Text(text = user.fullname)
                        Text(text = user.email)
                        Text(text = themeState.value)


                        Switch(
                            checked = themeState.value == Constants.DARK_MODE,
                            onCheckedChange = {
                                if (it){
                                    viewModel.setTheme(themeValue = Constants.DARK_MODE)
                                }else{
                                    viewModel.setTheme(themeValue = Constants.LIGHT_MODE)

                                }
                            })
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