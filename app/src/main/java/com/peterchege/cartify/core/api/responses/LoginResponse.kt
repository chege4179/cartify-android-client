package com.peterchege.cartify.core.api.responses

import com.peterchege.cartify.domain.models.User

data class LoginResponse (
    val msg:String,
    val success:Boolean,
    val user: User?,
    )