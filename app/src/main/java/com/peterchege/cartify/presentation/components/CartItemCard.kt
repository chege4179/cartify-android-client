package com.peterchege.cartify.presentation.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    onProductNavigate:(String) -> Unit,
    onRemoveCartItem:(String)->Unit,
    onReduceAmount:(Int,String) -> Unit,
    onIncreaseAmount:(Int,String) -> Unit
){
    Card(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(170.dp)
            .background(Color.White)
            .clickable {
                onProductNavigate(cartItem.id)
            }
        ,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                    contentDescription ="Product Image",
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
                    )
                    Text(
                        text = "Ksh ${cartItem.price} /=",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Subtotal : Ksh ${cartItem.quantity * cartItem.price} /=",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
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
                Icon(
                    Icons.Default.FavoriteBorder,

                    contentDescription = "Like",
                    modifier = Modifier
                        .clickable {

                        }

                )
                TextButton(
                    onClick = {
                        onRemoveCartItem(cartItem.id)
                }) {
                    Text("DELETE")

                }
                Icon(
                    Icons.Default.RemoveCircle,
                    contentDescription = "Reduce Amount",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .clickable {
                            onReduceAmount(cartItem.quantity,cartItem.id)
                        }

                )
                Text(
                    text = cartItem.quantity.toString()
                )
                Icon(
                    Icons.Default.AddCircle,
                    contentDescription = "Increase Amount",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .clickable {
                            onIncreaseAmount(cartItem.quantity,cartItem.id)

                        }

                )


            }


        }




    }
}