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
package com.peterchege.cartify.presentation.screens.search_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.peterchege.cartify.core.util.Screens
import com.peterchege.cartify.core.util.categories
import com.peterchege.cartify.domain.state.ProductsUiState
import com.peterchege.cartify.domain.state.SearchProductsUiState
import com.peterchege.cartify.presentation.components.CategoryCard
import com.peterchege.cartify.presentation.components.ProductCard

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    navController: NavController,
    navHostController: NavHostController,
    viewModel:SearchScreenViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    Scaffold(
        modifier = Modifier.fillMaxSize().padding(10.dp)
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.onBackground)
                ,
            value = viewModel.searchTerm.value,
            onValueChange = {
                viewModel.onChangeSearchTerm(query = it,)
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Product",
                    modifier = Modifier.size(26.dp),
                    tint = MaterialTheme.colors.primary
                )
            },
            placeholder = {
                Text(
                    text = "Search ....",
                    style = TextStyle(
                        color = MaterialTheme.colors.primary
                    )
                )
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                color = MaterialTheme.colors.primary,
                fontSize = 19.sp,
                textAlign = TextAlign.Start
            )

        )

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (uiState) {
                is SearchProductsUiState.Idle -> {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("Error Message"),
                        style = TextStyle(color = MaterialTheme.colors.primary),
                        text = uiState.message
                    )
                }
                is SearchProductsUiState.Loading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("loader")
                    )
                }
                is SearchProductsUiState.Success -> {
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

                                        },
                                        removeFromWishList = {

                                        },
                                        isWishList = false
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                            item {
                                Text(text = "Test element")
                            }

                        }

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                is SearchProductsUiState.Error -> {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("Error Message"),
                        style = TextStyle(color = MaterialTheme.colors.primary),
                        text = uiState.errorMessage
                    )

                }
            }
        }

    }

}