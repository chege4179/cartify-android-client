package com.peterchege.cartify.ui.screens.dashboard_screens.orders_screen.tabs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun PendingOrdersTab(){
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Pending Orders")

    }

}