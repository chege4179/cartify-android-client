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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.peterchege.cartify.core.room.entities.CartItem

@ExperimentalCoilApi
@Composable
fun CartItemCard(
    cartItem: CartItem,
    onProductNavigate: (String) -> Unit,
    onRemoveCartItem: (String) -> Unit,
    onReduceAmount: (Int, String) -> Unit,
    onIncreaseAmount: (Int, String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(170.dp)
            .clickable {
                onProductNavigate(cartItem.id)
            },
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.onBackground)
                .padding(vertical = 10.dp)

            ,

            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                val painter = rememberImagePainter(
                    data = cartItem.imageUrl,
                    builder = {
                        crossfade(true)

                    },
                )
                Image(
                    painter = painter,
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .fillMaxWidth(0.33f)
                        .height(110.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Text(
                        text = cartItem.name,
                        fontSize = 20.sp,
                        style = TextStyle(color = MaterialTheme.colors.primary),
                    )
                    Text(
                        text = "Ksh ${cartItem.price} /=",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(color = MaterialTheme.colors.primary),
                    )
                    Text(
                        text = "Subtotal : Ksh ${cartItem.quantity * cartItem.price} /=",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(color = MaterialTheme.colors.primary),
                    )

                }


            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                    )
                }
                TextButton(
                    onClick = {
                        onRemoveCartItem(cartItem.id)
                    }) {
                    Text("DELETE")

                }
                IconButton(
                    onClick = {
                        onReduceAmount(cartItem.quantity, cartItem.id)
                    }) {
                    Icon(
                        imageVector = Icons.Default.RemoveCircle,
                        contentDescription = "Reduce Amount",
                        tint = MaterialTheme.colors.primary,
                    )

                }
                Text(
                    text = cartItem.quantity.toString(),
                    style = TextStyle(color = MaterialTheme.colors.primary),
                )
                IconButton(
                    onClick = {
                        onIncreaseAmount(cartItem.quantity, cartItem.id)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Increase Amount",
                        tint = MaterialTheme.colors.primary,
                    )
                }
            }
        }
    }
}