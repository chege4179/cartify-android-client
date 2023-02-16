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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.cartify.presentation.components.CartIconComponent
import com.peterchege.cartify.presentation.components.CartItemCard
import com.peterchege.cartify.presentation.components.SubtotalCard
import com.peterchege.cartify.core.util.Screens


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    val cart = viewModel.cart.collectAsStateWithLifecycle()
    val user = viewModel.user.collectAsStateWithLifecycle(initialValue = null)
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
                        CartIconComponent(
                            navController = navController,
                            cartCount =cart.value.size)
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            if (viewModel.isLoading.value){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ){
                items(items = cart.value){ cartItem ->
                    CartItemCard(
                        cartItem = cartItem,
                        onProductNavigate ={
                            navController.navigate(Screens.PRODUCT_SCREEN + "/$it")

                        },
                        onRemoveCartItem = {
                            viewModel.removeFromCart(it)
                        },
                        onIncreaseAmount = { quantity,id ->
                            viewModel.increaseCartItemQuantity(quantity = quantity, id = id)

                        },
                        onReduceAmount = { quantity,id ->
                            viewModel.reduceCartItemQuantity(quantity = quantity,id = id)

                        },
                    )
                }
                
                item {
                    if(cart.value.isNotEmpty()){
                        val sum = cart.value.map { it.quantity * it.price }.sum()
                        SubtotalCard(
                            total = sum,
                            isLoggedIn = user.value != null ,
                            proceedToCheckOut ={
                                viewModel.proceedToOrder(
                                    total = it,
                                    context = context,
                                    scaffoldState = scaffoldState,
                                )

                            }
                        )
                    }else{
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