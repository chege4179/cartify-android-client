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
package com.peterchege.cartify.presentation.screens.product_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.peterchege.cartify.domain.models.Product
import com.peterchege.cartify.presentation.components.CartIconComponent
import com.peterchege.cartify.presentation.components.LoadingComponent
import com.peterchege.cartify.presentation.components.PagerIndicator
import com.peterchege.cartify.presentation.theme.Grey100
import kotlinx.coroutines.launch

@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun ProductScreen(
    navigateToCartScreen: () -> Unit,
    viewModel: ProductScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProductScreenContent(
        uiState = uiState,
        addToCart = viewModel::addToCart,
        navigateToCartScreen = navigateToCartScreen,
        addToWishList = viewModel::addToWishList
    )

}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun ProductScreenContent(
    uiState: ProductScreenUiState,
    addToCart: (Product) -> Unit,
    navigateToCartScreen :() -> Unit,
    addToWishList:(Product) ->Unit,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.onBackground,
                title = {
                    if (uiState is ProductScreenUiState.Success) {
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
                                text = uiState.product.name,
                                style = TextStyle(color = MaterialTheme.colors.primary)
                            )
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
            is ProductScreenUiState.Loading -> {
                LoadingComponent()
            }
            is ProductScreenUiState.Error -> {

            }
            is ProductScreenUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        val pagerState1 = rememberPagerState(initialPage = 0)
                        val coroutineScope = rememberCoroutineScope()
                        HorizontalPager(
                            count = uiState.product.images.size,
                            state = pagerState1
                        ) { image ->
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                SubcomposeAsyncImage(
                                    model = uiState.product.images[image].url,
                                    loading = {
                                        Box(modifier = Modifier.fillMaxSize()) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.align(
                                                    Alignment.Center
                                                )
                                            )
                                        }
                                    },
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp),
                                    contentDescription = "Product Images"
                                )
                                Box(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .width(45.dp)
                                        .align(Alignment.TopEnd)
                                        .height(25.dp)
                                        .clip(RoundedCornerShape(15.dp))
                                        .background(Color.White)

                                ) {
                                    Text(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .padding(horizontal = 3.dp),
                                        textAlign = TextAlign.Start,
                                        fontSize = 17.sp,
                                        text = "${image + 1}/${uiState.product.images}",
                                        style = TextStyle(color = MaterialTheme.colors.primary)
                                    )
                                }

                            }

                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp)
                        ) {
                            PagerIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                pagerState = pagerState1
                            ) {
                                coroutineScope.launch {
                                    pagerState1.scrollToPage(it)
                                }
                            }
                        }
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)

                        ) {
                            Text(
                                text = uiState.product.name,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start,
                                fontSize = 20.sp,
                                style = TextStyle(color = MaterialTheme.colors.primary)
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight.Bold,
                                text = "Ksh ${uiState.product.price} /=",
                                fontSize = 25.sp,
                                style = TextStyle(color = MaterialTheme.colors.primary)
                            )
                            Text(
                                text = "Description :",
                                style = TextStyle(color = MaterialTheme.colors.primary)
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = uiState.product.description,
                                style = TextStyle(color = MaterialTheme.colors.primary)

                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.onBackground
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                shape = RoundedCornerShape(20),
                                onClick = {
                                    addToCart(uiState.product)

                                }) {
                                Text(
                                    text = "Add To Cart",
                                    style = TextStyle(color = MaterialTheme.colors.primary)
                                )

                            }
                            Spacer(modifier = Modifier.height(15.dp))

                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.onBackground
                                ),
                                shape = RoundedCornerShape(20),
                                onClick = {
                                    addToWishList(uiState.product)

                                }) {
                                Text(
                                    text = "Add To WishList",
                                    style = TextStyle(color = MaterialTheme.colors.primary)
                                )

                            }
                        }
                    }
                }
            }
        }

    }
}