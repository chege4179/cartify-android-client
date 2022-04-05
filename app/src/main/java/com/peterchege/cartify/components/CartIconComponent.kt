package com.peterchege.cartify.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.peterchege.cartify.util.Screens


@Composable
fun CartIconComponent(
    navController: NavController,
    cartCount:Int,
){
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        BottomNavigationItem(
            icon = {
                BadgedBox(badge = {
                    if(cartCount > 0){
                        Badge {
                            Text(
                                text = cartCount.toString(),
                                fontSize = 15.sp
                            )
                        }
                    }
                }) {
                    Icon(
                        Icons.Filled.AddShoppingCart,
                        contentDescription = "Favorite",
                        Modifier.size(26.dp)
                    )
                }

            },
            selected = false,
            onClick = {
                navController.navigate(Screens.CART_SCREEN)
            })

    }
}


//        if (cartCount > 0){
//
//            BadgedBox(
//                badge = {
//                    Badge {
//
//                        Text(
//                            text = cartCount.toString(),
//                            fontSize = 16.sp,
//
//                            )
//
//                    }
//                })
//            {
//                Icon(
//                    Icons.Default.AddShoppingCart,
//                    contentDescription = "Cart",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight(0.7f)
//                        .clickable {
//                            navController.navigate(Screens.CART_SCREEN)
//                        },
//
//                    )
//            }
//        }else{
//            Icon(
//                Icons.Default.AddShoppingCart,
//                contentDescription = "Cart",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight(0.7f)
//                    .clickable {
//                        navController.navigate(Screens.CART_SCREEN)
//                    },
//
//                )
//        }