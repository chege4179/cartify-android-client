package com.peterchege.cartify.ui.screens.dashboard_screens.wishlist_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.peterchege.cartify.components.CartIconComponent
import com.peterchege.cartify.components.ProductCard
import com.peterchege.cartify.room.entities.toProduct
import com.peterchege.cartify.util.Screens


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalFoundationApi
@Composable
fun WishListScreen(
    navController: NavController,
    wishListViewModel: WishListViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState=scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
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
                            text = "My WishList"
                        )
                        CartIconComponent(
                            navController = navController,
                            cartCount =wishListViewModel.cartItems.value.size
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp)
        ) {
            if(wishListViewModel.wishlist.value.isEmpty()){
                Text("Your wishlist is empty")
            }else{
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.background(MaterialTheme.colors.background)
                ){
                    items(items = wishListViewModel.wishlist.value){ product ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ProductCard(
                                product = product.toProduct(),
                                onNavigateToProductScreen = { id ->
                                    navController.navigate(Screens.PRODUCT_SCREEN + "/${id}")
                                },
                                onAddToWishList = {

                                },
                                removeFromWishList = {
                                    wishListViewModel.deleteProductfromRoom(it._id)

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