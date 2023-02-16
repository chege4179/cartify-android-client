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
package com.peterchege.cartify.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.peterchege.cartify.core.util.Screens
import com.peterchege.cartify.presentation.screens.cart_screen.CartScreen
import com.peterchege.cartify.presentation.screens.dashboard_screens.DashBoardScreen
import com.peterchege.cartify.presentation.screens.login_screen.LoginScreen
import com.peterchege.cartify.presentation.screens.product_screen.ProductScreen
import com.peterchege.cartify.presentation.screens.sign_up_screen.SignUpScreen

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun Navigation(
    navController: NavHostController
){
    NavHost(
        navController =navController,
        startDestination = Screens.DASHBOARD_SCREEN,
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        }
    ){
        composable(Screens.DASHBOARD_SCREEN){
            DashBoardScreen(navHostController = navController)
        }
        composable(Screens.PRODUCT_SCREEN + "/{id}"){
            ProductScreen(navController = navController, navHostController = navController)
        }
        composable(Screens.CART_SCREEN){
            CartScreen(navController = navController)
        }
        composable(Screens.LOGIN_SCREEN){
            LoginScreen(navController = navController)
        }
        composable(Screens.SIGN_UP_SCREEN){
            SignUpScreen(navController = navController)
        }
    }

}