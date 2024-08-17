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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peterchege.cartify.core.util.UiEvent
import com.peterchege.cartify.core.util.categories
import com.peterchege.cartify.domain.models.Product
import com.peterchege.cartify.presentation.components.CartIconComponent
import com.peterchege.cartify.presentation.components.CategoryCard
import com.peterchege.cartify.presentation.components.LoadingComponent
import com.peterchege.cartify.presentation.components.ProductCard
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navigateToProductScreen:(String) -> Unit,
    navigateToCartScreen:() -> Unit,
    navigateToSearchScreen:() -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel(),
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.syncProducts()
    }

    HomeScreenContent(
        uiState = uiState,
        eventFlow = viewModel.eventFlow,
        navigateToProductScreen = navigateToProductScreen,
        navigateToCartScreen = navigateToCartScreen,
        navigateToSearchScreen = navigateToSearchScreen,
        addToWishList = viewModel::addToWishList
    )
}



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalFoundationApi
@Composable
fun HomeScreenContent(
    uiState:HomeScreenUiState,
    eventFlow:SharedFlow<UiEvent>,
    navigateToProductScreen:(String) -> Unit,
    navigateToCartScreen:() -> Unit,
    navigateToSearchScreen:() -> Unit,
    addToWishList:(Product) -> Unit,
) {
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
                                navigateToSearchScreen()
                            }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Product",
                                modifier = Modifier.size(26.dp),
                                tint = MaterialTheme.colors.primary
                            )
                        }
                        if (uiState is HomeScreenUiState.Success){
                            CartIconComponent(
                                navigateToCartScreen = navigateToCartScreen,
                                cartCount = uiState.cart.size
                            )
                        }

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


                is HomeScreenUiState.Loading -> {
                    LoadingComponent()
                }
                is HomeScreenUiState.Error -> {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("Error Message"),
                        style = TextStyle(color = MaterialTheme.colors.primary),
                        text = uiState.message
                    )

                }
                is HomeScreenUiState.Success -> {
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
                                CategoryCard(
                                    categoryItem = category
                                )

                            }

                        }
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            Modifier
                                .background(color = MaterialTheme.colors.background)
                                .testTag(tag = "products_list")
                        ) {
                            items(items = uiState.products) { product ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    ProductCard(
                                        product = product,
                                        onNavigateToProductScreen = { id ->
                                            navigateToProductScreen(id)

                                        },
                                        onAddToWishList = {
                                            addToWishList(it)
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