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
package com.peterchege.cartify.presentation.screens.dashboard_screens.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.cartify.core.util.Resource
import com.peterchege.cartify.presentation.components.CartIconComponent
import com.peterchege.cartify.presentation.components.CategoryCard
import com.peterchege.cartify.core.util.Screens
import com.peterchege.cartify.presentation.components.ProductCard
import com.peterchege.cartify.core.util.categories


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    navController: NavController,
    navHostController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val homeScreenState = viewModel.productsUseCase.collectAsStateWithLifecycle()

    val cart = viewModel.cart.collectAsStateWithLifecycle()
    Scaffold(
        scaffoldState = scaffoldState,
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
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth(0.87f)
                                .fillMaxHeight()
                                .background(Color.White)
                                .padding(top = 5.dp)
                                .padding(horizontal = 5.dp),


                            value = viewModel.searchTerm.value,
                            onValueChange = {
                                viewModel.onChangeSearchTerm(
                                    query = it,
                                    scaffoldState = scaffoldState,
                                    context = context
                                )
                            },
                            maxLines = 1,
                            singleLine = true,
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontSize = 19.sp,
                                textAlign = TextAlign.Start
                            )

                        )
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            CartIconComponent(
                                navController = navHostController,
                                cartCount = cart.value.size
                            )
                        }


                    }

                },
                backgroundColor = MaterialTheme.colors.onBackground)
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (homeScreenState.value) {
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is Resource.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colors.background)
                            .padding(horizontal = 10.dp)
                            .padding(top = 10.dp)
                    ) {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 2.dp)
                        ) {
                            items(categories) { category ->
                                CategoryCard(navController = navController, categoryItem = category)

                            }

                        }
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            Modifier.background(MaterialTheme.colors.background)
                        ) {
                            items(viewModel.products.value) { product ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    ProductCard(
                                        product = product,
                                        onNavigateToProductScreen = { id ->
                                            navHostController.navigate(Screens.PRODUCT_SCREEN + "/${id}")

                                        },
                                        onAddToWishList = {
                                            viewModel.addToWishList(
                                                it,
                                                scaffoldState = scaffoldState
                                            )
                                        },
                                        removeFromWishList = {

                                        },
                                        isWishList = false
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }

                        }

                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
                is Resource.Error -> {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = homeScreenState.value.message ?: "An unexpected error occurred"
                    )

                }
            }
        }


    }

}