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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.cartify.core.util.Screens
import com.peterchege.cartify.core.util.categories
import com.peterchege.cartify.domain.state.ProductsUiState
import com.peterchege.cartify.presentation.components.CartIconComponent
import com.peterchege.cartify.presentation.components.CategoryCard
import com.peterchege.cartify.presentation.components.ProductCard


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    navController: NavController,
    navHostController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val cart = viewModel.cart.collectAsStateWithLifecycle()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Text(
                            text = "Cartify E-commerce App",
                            modifier = Modifier.fillMaxWidth(0.73f),
                            style = TextStyle(
                                color = MaterialTheme.colors.primary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                        IconButton(
                            onClick = {
                                navHostController.navigate(route = Screens.SEARCH_SCREEN)
                            }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Product",
                                modifier = Modifier.size(26.dp),
                                tint = MaterialTheme.colors.primary
                            )
                        }
                        CartIconComponent(
                            navController = navHostController,
                            cartCount = cart.value.size
                        )
                    }

                },
                backgroundColor = MaterialTheme.colors.onBackground
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (uiState) {
                is ProductsUiState.Idle -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("loader")
                    )
                }

                is ProductsUiState.Loading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("loader")
                    )
                }
                is ProductsUiState.Error -> {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("Error Message"),
                        style = TextStyle(color = MaterialTheme.colors.primary),
                        text = uiState.errorMessage
                    )

                }
                is ProductsUiState.Success -> {
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
                            items(items = categories) { category ->
                                CategoryCard(navController = navController, categoryItem = category)

                            }

                        }
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            Modifier
                                .background(color = MaterialTheme.colors.background)
                                .testTag(tag = "products_list")
                        ) {
                            items(items = uiState.data.products) { product ->
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
            }
        }
    }
}