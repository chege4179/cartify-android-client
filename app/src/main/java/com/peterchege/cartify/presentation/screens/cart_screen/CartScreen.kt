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
package com.peterchege.cartify.presentation.screens.cart_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peterchege.cartify.core.util.UiEvent
import com.peterchege.cartify.presentation.components.CartIconComponent
import com.peterchege.cartify.presentation.components.CartItemCard
import com.peterchege.cartify.presentation.components.LoadingComponent
import com.peterchege.cartify.presentation.components.SubtotalCard
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CartScreen(
    navigateToProductScreen: (String) -> Unit,
    viewModel: CartScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CartScreenContent(
        uiState = uiState,
        eventFlow = viewModel.eventFlow,
        isLoading = viewModel.isLoading.value,
        removeFromCart = {
            viewModel.removeFromCart(it)
        },
        reduceCartItemQuantity = { quantity, id ->
            viewModel.reduceCartItemQuantity(quantity, id)
        },
        increaseCartItemQuantity = { quantity, id ->
            viewModel.increaseCartItemQuantity(quantity, id)
        },
        processOrder = { /*TODO*/ },
        navigateToProductScreen = navigateToProductScreen
    )
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CartScreenContent(
    uiState: CartScreenUiState,
    eventFlow: SharedFlow<UiEvent>,
    isLoading: Boolean,
    removeFromCart: (String) -> Unit,
    reduceCartItemQuantity: (Int, String) -> Unit,
    increaseCartItemQuantity: (Int, String) -> Unit,
    processOrder: () -> Unit,
    navigateToProductScreen: (String) -> Unit,
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

                is UiEvent.Navigate -> {}
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
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
                            text = "My Cart",
                            style = TextStyle(color = MaterialTheme.colors.primary),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        if (uiState is CartScreenUiState.Success) {
                            CartIconComponent(
                                navigateToCartScreen = { },
                                cartCount = uiState.cart.size
                            )
                        }

                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            when (uiState) {
                is CartScreenUiState.Loading -> {
                    LoadingComponent()
                }

                is CartScreenUiState.Error -> {

                }

                is CartScreenUiState.Success -> {
                    val cart = uiState.cart
                    val user = uiState.user
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        items(items = cart) { cartItem ->
                            CartItemCard(
                                cartItem = cartItem,
                                onProductNavigate = {
                                    navigateToProductScreen(it)
                                },
                                onRemoveCartItem = {
                                    removeFromCart(it)
                                },
                                onIncreaseAmount = { quantity, id ->
                                    increaseCartItemQuantity(quantity, id)

                                },
                                onReduceAmount = { quantity, id ->
                                    reduceCartItemQuantity(quantity, id)

                                },
                            )
                        }

                        item {
                            if (cart.isNotEmpty()) {
                                val sum = cart.sumOf { it.quantity * it.price }
                                SubtotalCard(
                                    total = sum,
                                    isLoggedIn = user != null,
                                    proceedToCheckOut = {
//                                        proceedToOrder(
//                                            total = it,
//
//                                        )

                                    }
                                )
                            } else {
                                Text(
                                    text = "Your cart is empty",
                                    style = TextStyle(color = MaterialTheme.colors.primary),
                                )
                            }
                        }

                    }
                }
            }

        }
    }
}