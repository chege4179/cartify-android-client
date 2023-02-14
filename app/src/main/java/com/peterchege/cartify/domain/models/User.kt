package com.peterchege.cartify.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User (
    val _id:String,
    val fullname:String,
    val phoneNumber:String,
    val email:String,
    val password:String,
    val address:String,
)