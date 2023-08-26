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
package com.peterchege.cartify.presentation.screens.dashboard_screens.wishlist_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peterchege.cartify.presentation.components.CartIconComponent
import com.peterchege.cartify.presentation.components.LoadingComponent
import com.peterchege.cartify.presentation.components.ProductCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WishListScreen(
    navigateToCartScreen:() -> Unit,
    navigateToProductScreen:(String) -> Unit,
    wishListScreenViewModel: WishListScreenViewModel = hiltViewModel()
){
    val uiState by wishListScreenViewModel.uiState.collectAsStateWithLifecycle()
    WishListScreenContent(
        navigateToCartScreen = navigateToCartScreen,
        navigateToProductScreen = navigateToProductScreen,
        uiState = uiState,
        deleteFromWishList = { wishListScreenViewModel.deleteWishListItem(it) }
    )
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalFoundationApi
@Composable
fun WishListScreenContent(
    navigateToCartScreen:() -> Unit,
    navigateToProductScreen:(String) -> Unit,
    uiState: WishListScreenUiState,
    deleteFromWishList:(String) -> Unit,

) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState=scaffoldState,
        modifier = Modifier.fillMaxSize(),
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
                            text = "My WishList",
                            style = TextStyle(color = MaterialTheme.colors.primary),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        if (uiState is WishListScreenUiState.Success){
                            CartIconComponent(
                                navigateToCartScreen = navigateToCartScreen,
                                cartCount = uiState.cart.size
                            )
                        }

                    }
                }
            )
        }
    ) {
        when(uiState){
            is WishListScreenUiState.Loading -> {
                LoadingComponent()
            }
            is WishListScreenUiState.Error -> {

            }
            is WishListScreenUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp)
                        .padding(top = 10.dp)
                ) {
                    if(uiState.wishlistItems.isEmpty()){
                        Text("Your wishlist is empty")
                    }else{
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.background(MaterialTheme.colors.background)
                        ){
                            items(items = uiState.wishlistItems){ product ->
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
                                            deleteFromWishList(it._id)
                                        },
                                        isWishList = true
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }

                        }
                    }

                }
            }
        }




    }

}