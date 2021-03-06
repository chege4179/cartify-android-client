package com.peterchege.cartify.ui.screens.cart_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.peterchege.cartify.components.CartIconComponent
import com.peterchege.cartify.components.CartItemCard
import com.peterchege.cartify.components.SubtotalCard
import com.peterchege.cartify.util.Screens


@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
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
                            text = "My Cart"
                        )
                        CartIconComponent(
                            navController = navController,
                            cartCount =viewModel.cart.value.size)
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            if (viewModel.isLoading.value){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ){
                items(viewModel.cart.value){ cartItem ->
                    CartItemCard(
                        cartItem = cartItem,
                        onProductNavigate ={
                            navController.navigate(Screens.PRODUCT_SCREEN + "/$it")

                        },
                        onRemoveCartItem = {
                            viewModel.removeFromCart(it)
                        },
                        onIncreaseAmount = { quantity,id ->
                            viewModel.increaseCartItemQuantity(quantity = quantity, id = id)

                        },
                        onReduceAmount = { quantity,id ->
                            viewModel.reduceCartItemQuantity(quantity = quantity,id = id)

                        },
                    )
                }
                
                item {
                    if(viewModel.cart.value.isNotEmpty()){
                        val sum = viewModel.cart.value.map { it.quantity * it.price }.sum()
                        SubtotalCard(
                            total = sum,
                            isLoggedIn = viewModel.user.value != null ,
                            proceedToCheckOut ={
                                viewModel.proceedToOrder(
                                    total = it,
                                    context = context,
                                    scaffoldState = scaffoldState,
                                )

                            }
                        )
                    }else{
                        Text(text = "Your cart is empty")
                    }
                }

            }
        }
    }
}