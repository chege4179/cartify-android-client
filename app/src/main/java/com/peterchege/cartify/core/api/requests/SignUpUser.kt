package com.peterchege.cartify.core.api.requests

data class SignUpUser (
    val fullname:String,
    val email:String,
    val phoneNumber:String,
    val password:String,
    val address:String

)