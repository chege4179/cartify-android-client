package com.peterchege.cartify.ui.screens.product_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.peterchege.cartify.components.CartIconComponent
import com.peterchege.cartify.ui.theme.Grey100
import com.peterchege.cartify.util.Screens


@ExperimentalCoilApi
@ExperimentalPagerApi

@Composable
fun ProductScreen(
    navController: NavController,
    navHostController: NavController,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
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
                                cartCount =viewModel.cartCount.value )
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
                    HorizontalPager(count = product.images.size) { image ->
                        Image(
                            painter = rememberImagePainter(
                                data = product.images[image].url,
                                builder = {
                                    crossfade(true)

                                }
                            ),
                            contentDescription = "Post Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        )
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
                                .height(50.dp),
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