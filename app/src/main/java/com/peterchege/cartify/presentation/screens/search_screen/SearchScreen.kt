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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peterchege.cartify.core.util.categories
import com.peterchege.cartify.presentation.components.CategoryCard
import com.peterchege.cartify.presentation.components.ProductCard

@Composable
fun SearchScreen(
    navigateToProductScreen:(String) -> Unit,
    viewModel:SearchScreenViewModel = hiltViewModel(),
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchScreenContent(
        uiState = uiState,
        searchTerm = viewModel.searchTerm.value,
        onChangeSearchTerm = viewModel::onChangeSearchTerm,
        navigateToProductScreen = navigateToProductScreen,
    )
}



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreenContent(
    uiState:SearchProductScreensUiState,
    searchTerm:String,
    onChangeSearchTerm:(String) -> Unit,
    navigateToProductScreen:(String) -> Unit,

) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.onBackground)
                ,
            value = searchTerm,
            onValueChange = {
                onChangeSearchTerm(it)
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
                is SearchProductScreensUiState.Idle -> {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("Error Message"),
                        style = TextStyle(color = MaterialTheme.colors.primary),
                        text = "Search an Item"
                    )
                }
                is SearchProductScreensUiState.Searching -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("loader")
                    )
                }
                is SearchProductScreensUiState.ResultsFound -> {
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
                                CategoryCard( categoryItem = category)

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
                is SearchProductScreensUiState.Error -> {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("Error Message"),
                        style = TextStyle(color = MaterialTheme.colors.primary),
                        text = uiState.message
                    )

                }
            }
        }

    }

}