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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.peterchege.cartify.core.util.Constants
import com.peterchege.cartify.presentation.components.CartIconComponent
import com.peterchege.cartify.core.util.Screens
import com.peterchege.cartify.core.util.generateAvatarURL
import com.peterchege.cartify.presentation.components.NoLoggedInUserScreenComponent
import com.peterchege.cartify.presentation.components.SettingsCard

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navController: NavController,
    navHostController: NavController,
    viewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val themeState by viewModel.theme.collectAsStateWithLifecycle()
    val user by viewModel.user.collectAsStateWithLifecycle()
    val cart by viewModel.cartItems.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.onBackground,
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
                            text = "Profile",
                            style = TextStyle(
                                color = MaterialTheme.colors.primary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                        CartIconComponent(
                            cartCount = cart.size,
                            navigateToCartScreen = { }
                        )
                    }
                }
            )
        }

    ) {
        if (user == null) {
            NoLoggedInUserScreenComponent(navHostController = navHostController)
        } else if (user!!._id == "") {
            NoLoggedInUserScreenComponent(navHostController = navHostController)
        } else {
            user?.let { user ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    SubcomposeAsyncImage(
                        model = generateAvatarURL(user.fullname),
                        contentDescription = "User Profile Photo",
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                            .clip(CircleShape),
                        loading = { CircularProgressIndicator(modifier = Modifier.size(10.dp)) },
                        error = {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = "No internet"
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = user.fullname,
                        style = TextStyle(color = MaterialTheme.colors.primary),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = user.email,
                        style = TextStyle(color = MaterialTheme.colors.primary),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    SettingsCard(
                        title = "Dark Theme",
                        checked = themeState == Constants.DARK_MODE,
                        onCheckedChange = {
                            if (it) {
                                viewModel.setTheme(Constants.DARK_MODE)
                            } else {
                                viewModel.setTheme(Constants.LIGHT_MODE)
                            }

                        }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.onBackground
                        ),
                        onClick = {
                            viewModel.logoutUser()
                        }) {
                        Text(
                            text = "Log Out",
                            style = TextStyle(color = MaterialTheme.colors.primary)
                        )

                    }

                }
            }
        }

    }

}