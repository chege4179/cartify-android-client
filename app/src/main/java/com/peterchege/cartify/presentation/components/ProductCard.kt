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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.peterchege.cartify.domain.models.Product


@ExperimentalCoilApi
@Composable
fun ProductCard(
    product: Product,
    onNavigateToProductScreen: (String) -> Unit,
    onAddToWishList: (Product) -> Unit,
    removeFromWishList: (Product) -> Unit,
    isWishList: Boolean

) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.onBackground)

            .clickable {
                onNavigateToProductScreen(product._id)
            },

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.onBackground)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = rememberImagePainter(
                    data = product.images[0]?.url,
                    builder = {
                        crossfade(true)

                    }
                ),
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .height(150.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.85f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = product.name,
                        style = TextStyle(color = MaterialTheme.colors.primary)
                    )
                    Text(
                        text = "Ksh ${product.price}/=",
                        style = TextStyle(color = MaterialTheme.colors.primary)
                    )

                }
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isWishList) {
                        IconButton(onClick = {
                            removeFromWishList(product)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove from wishlist",
                                tint = MaterialTheme.colors.primary

                            )
                        }

                    } else {
                        IconButton(
                            onClick = {
                                onAddToWishList(product)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.BookmarkBorder,
                                contentDescription = "Add to wishlist",
                                tint = MaterialTheme.colors.primary

                            )
                        }

                    }

                }

            }
        }
    }
}