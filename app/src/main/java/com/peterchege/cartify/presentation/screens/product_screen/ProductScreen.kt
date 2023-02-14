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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.peterchege.cartify.presentation.components.CartIconComponent
import com.peterchege.cartify.presentation.components.PagerIndicator
import com.peterchege.cartify.presentation.theme.Grey100
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalCoilApi
@ExperimentalPagerApi

@Composable
fun ProductScreen(
    navController: NavController,
    navHostController: NavController,
    viewModel: ProductScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val cart = viewModel.cart.collectAsStateWithLifecycle()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    viewModel.product.value?.let {
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
                                text = it.name
                            )
                            CartIconComponent(
                                navController = navHostController,
                                cartCount =cart.value.size )
                        }
                    }
                }
            )
        }
    ) {
        viewModel.product.value?.let { product ->
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    val pagerState1 = rememberPagerState(initialPage = 0)
                    val coroutineScope = rememberCoroutineScope()
                    HorizontalPager(
                        count = product.images.size,
                        state = pagerState1
                    ) { image ->
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ){
                            SubcomposeAsyncImage(
                                model = product.images[image].url,
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

                            ){
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(horizontal = 3.dp),
                                    textAlign = TextAlign.Start,
                                    fontSize = 17.sp,
                                    text = "${image + 1}/${product.images.size}"
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
                            text = product.name,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            fontSize = 20.sp
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            fontWeight = FontWeight.Bold,
                            text = "Ksh ${product.price} /=",
                            fontSize = 25.sp,
                        )
                        Text(
                            text = "Description :",
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = product.description,

                            )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp),
                            shape = RoundedCornerShape(20),
                            onClick = {
                            viewModel.addToCart()

                        }) {
                            Text(text = "Add To Cart")

                        }
                        Spacer(modifier = Modifier.height(15.dp))

                        OutlinedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Grey100)
                            ,
                            shape = RoundedCornerShape(20),
                            onClick = {
                            viewModel.addToWishList(
                                product = product,
                                scaffoldState = scaffoldState
                            )

                        }) {
                            Text(text = "Add To WishList")

                        }


                    }
                }
                item {

                }
            }
        }
    }
}