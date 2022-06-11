package com.peterchege.cartify.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.peterchege.cartify.models.Product
import com.peterchege.cartify.ui.theme.Grey100
import com.peterchege.cartify.ui.theme.Grey200


@ExperimentalCoilApi
@Composable
fun ProductCard(
    product: Product,
    onNavigateToProductScreen:(String) -> Unit,
    onAddToWishList:(Product) -> Unit,
    removeFromWishList:(Product) -> Unit,
    isWishList:Boolean

) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()

            .clickable {
                onNavigateToProductScreen(product._id)
            }
        ,

    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
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
                    Text(text = product.name)
                    Text(text = "Ksh ${product.price}/=")

                }
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isWishList){
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Remove from wishlist",
                            modifier = Modifier
                                .clickable {
                                    removeFromWishList(product)
                                }

                        )
                    }else{
                        Icon(
                            Icons.Default.BookmarkBorder,
                            contentDescription = "Add to wishlist",
                            modifier = Modifier
                                .clickable {
                                    onAddToWishList(product)
                                }

                        )
                    }

                }

            }
        }
    }
}