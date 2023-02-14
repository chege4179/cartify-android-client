package com.peterchege.cartify.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
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