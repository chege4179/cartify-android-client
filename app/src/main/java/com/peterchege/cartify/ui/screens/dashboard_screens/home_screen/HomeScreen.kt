package com.peterchege.cartify.ui.screens.dashboard_screens.home_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.peterchege.cartify.components.CartIconComponent
import com.peterchege.cartify.components.CategoryCard
import com.peterchege.cartify.util.Screens
import com.peterchege.cartify.components.ProductCard
import com.peterchege.cartify.util.categories


@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    navController: NavController,
    navHostController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            ,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .fillMaxHeight()
                        ,
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,

                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth(0.87f)
                                .fillMaxHeight()
                                .background(Color.White)
                                .padding(top = 5.dp)
                                .padding(horizontal = 5.dp)
                            ,


                            value = viewModel.searchTerm.value,
                            onValueChange ={
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
                                textAlign = TextAlign.Start)

                        )
                        Row(
                            modifier = Modifier.fillMaxSize().fillMaxWidth().fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ){
                            CartIconComponent(
                                navController = navHostController,
                                cartCount = viewModel.cartCount.value)
                        }


                    }

                }
                ,
                backgroundColor = MaterialTheme.colors.primary)
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (viewModel.isLoading.value){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            if (viewModel.isError.value){
                LaunchedEffect(scaffoldState.snackbarHostState){
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = viewModel.msg.value
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp)
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp)
                ){
                    items(categories){ category ->
                        CategoryCard(navController = navController, categoryItem = category)
                        Spacer(modifier = Modifier.width(10.dp))

                    }

                }
                LazyVerticalGrid(
                    cells = GridCells.Fixed(2),
                    Modifier.background(MaterialTheme.colors.background)
                ){
                    items(viewModel.products.value){ product ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ProductCard(
                                product = product,
                                onNavigateToProductScreen = { id ->
                                    navHostController.navigate(Screens.PRODUCT_SCREEN + "/${id}")

                                },
                                onAddToWishList = {
                                    viewModel.addToWishList(it, scaffoldState = scaffoldState)
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