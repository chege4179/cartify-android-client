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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
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

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.onBackground)
                .padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Total: ${total.toString()}",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(color = MaterialTheme.colors.primary),
            )
            Text(
                text = "VAT: ${(0.16 * total).toString()}",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(color = MaterialTheme.colors.primary),
            )
            Text(
                text = "Shipping Fee: 1000/=",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(color = MaterialTheme.colors.primary),
            )
            Text(
                text = "Full Total: ${finaltotal.toString()}",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(color = MaterialTheme.colors.primary),
            )
            if (isLoggedIn){
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                    ,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.background
                    ),
                    onClick = {
                        proceedToCheckOut(finaltotal)

                    }
                ) {
                    Text(
                        text = "Check Out",
                        style = TextStyle(color = MaterialTheme.colors.primary),
                    )
                }
            }else{
                Text(
                    text="Log In to proceed to check out",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(color = MaterialTheme.colors.primary),
                )
            }
        }
    }

}