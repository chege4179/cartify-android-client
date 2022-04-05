package com.peterchege.cartify.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SubtotalCard(
    total:Int,
    isLoggedIn:Boolean,
    proceedToCheckOut:(Long) ->Unit,

) {
    val finaltotal = Math.round(((1.16 * total) + 1000))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Total: ${total.toString()}",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "VAT: ${(0.16 * total).toString()}",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Shipping Fee: 1000/=",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Full Total: ${finaltotal.toString()}",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            if (isLoggedIn){
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                    ,
                    onClick = {
                        proceedToCheckOut(finaltotal)

                    }
                ) {
                    Text("Check Out")
                }
            }else{
                Text(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    text="Log In to proceed to check out",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }

}