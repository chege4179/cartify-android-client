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
package com.peterchege.cartify.presentation.components

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
import com.peterchege.cartify.core.util.Screens


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
                        imageVector = Icons.Filled.AddShoppingCart,
                        contentDescription = "Favorite",
                        modifier = Modifier.size(26.dp),
                        tint = MaterialTheme.colors.primary
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