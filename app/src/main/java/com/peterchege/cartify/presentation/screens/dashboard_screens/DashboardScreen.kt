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
package com.peterchege.cartify.presentation.screens.dashboard_screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.peterchege.cartify.core.util.Screens
import com.peterchege.cartify.domain.models.BottomNavItem
import com.peterchege.cartify.presentation.screens.dashboard_screens.home_screen.HomeScreen
import com.peterchege.cartify.presentation.screens.dashboard_screens.orders_screen.OrderScreen
import com.peterchege.cartify.presentation.screens.dashboard_screens.profile_screen.ProfileScreen
import com.peterchege.cartify.presentation.screens.dashboard_screens.wishlist_screen.WishListScreen

@Composable
fun BottomNavBar(
    items:List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick:(BottomNavItem) -> Unit
){
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.background,
        elevation = 5.dp
    ) {
        items.forEach{ item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected =selected ,
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Gray,
                onClick = { onItemClick(item) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (item.badgeCount > 0){
                            Icon(
                                imageVector = item.icon,
                                contentDescription =item.name
                            )
                        }else{
                            Icon(
                                imageVector = item.icon,
                                contentDescription =item.name
                            )
                        }
                        if (selected){
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp

                            )
                        }

                    }

                }
            )
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun DashBoardScreen(
    navHostController: NavHostController,
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                items = listOf(
                    BottomNavItem(
                        name="Home",
                        route = Screens.HOME_SCREEN  ,
                        icon = Icons.Default.Home
                    ),
                    BottomNavItem(
                        name="Wishlist",
                        route = Screens.WISHLIST_SCREEN   ,
                        icon = Icons.Default.Favorite
                    ),
                    BottomNavItem(
                        name="Orders",
                        route = Screens.ORDERS_SCREEN ,
                        icon = Icons.Filled.Receipt
                    ),
                    BottomNavItem(
                        name="Profile",
                        route = Screens.PROFILE_SCREEN ,
                        icon = Icons.Default.Person
                    )

                ),
                navController = navController,
                onItemClick ={
                    navController.navigate(it.route)
                }
            )
        }
    ) { innerPadding ->
        // Apply the padding globally to the whole BottomNavScreensController
        Box(modifier = Modifier.padding(innerPadding)) {
            DashboardNavigation(navController = navController, navHostController = navHostController)
        }

    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardNavigation(
    navController: NavHostController,
    navHostController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screens.HOME_SCREEN ){

        composable(
            route = Screens.HOME_SCREEN
        ){
            HomeScreen(navController = navController,navHostController = navHostController)
        }
        composable(
                route = Screens.WISHLIST_SCREEN
        ){
            WishListScreen(navController = navHostController)
        }

        composable(
            route = Screens.ORDERS_SCREEN
        ){
            OrderScreen(navController=  navHostController)
        }
        composable(
            route = Screens.PROFILE_SCREEN
        ){
            ProfileScreen(navController, navHostController = navHostController)
        }

    }

}