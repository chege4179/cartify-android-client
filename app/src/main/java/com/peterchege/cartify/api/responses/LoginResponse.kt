package com.peterchege.cartify.api.responses

import com.peterchege.cartify.models.User

data class LoginResponse (
    val msg:String,
    val success:Boolean,
    val user:User?,
    )