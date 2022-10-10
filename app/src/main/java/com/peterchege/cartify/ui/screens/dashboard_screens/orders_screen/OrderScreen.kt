package com.peterchege.cartify.ui.screens.dashboard_screens.orders_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.*
import com.peterchege.cartify.components.CartIconComponent
import com.peterchege.cartify.ui.screens.dashboard_screens.orders_screen.tabs.PastOrdersTab
import com.peterchege.cartify.ui.screens.dashboard_screens.orders_screen.tabs.PendingOrdersTab
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun OrderScreen(
    navController: NavController,
    viewModel:OrderViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
                            text = "My Orders"
                        )
                        CartIconComponent(
                            navController = navController,
                            cartCount =viewModel.cart.value.size)
                    }
                }
            )
        },

    ) {
        TabScreen()
    }
}



@ExperimentalPagerApi
@Composable
fun TabScreen() {
    val pagerState = rememberPagerState()
    Column(
        modifier = Modifier.background(Color.White)
    ) {
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState)
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf("Pending Orders","Past Orders")
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        divider = {
            TabRowDefaults.Divider(
                thickness = 2.dp,
                color = Color.Green
            )
        },
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                color = Color.White
            )
        }
    ) {
        list.forEachIndexed { index, _->
            Tab(
                text = {
                    Text(
                        list[index],
                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState) {
    HorizontalPager(state = pagerState ,count =2) { page ->
        when(page) {
            0 -> PendingOrdersTab()
            1 -> PastOrdersTab()

        }
    }
}
