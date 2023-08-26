package com.peterchege.cartify.data.remote

import com.peterchege.cartify.core.api.CartifyApi
import com.peterchege.cartify.core.api.NetworkResult
import com.peterchege.cartify.core.api.requests.LoginUser
import com.peterchege.cartify.core.api.requests.SignUpUser
import com.peterchege.cartify.core.api.responses.LoginResponse
import com.peterchege.cartify.core.api.responses.SignUpResponse
import com.peterchege.cartify.core.api.safeApiCall
import com.peterchege.cartify.core.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RemoteAuthDataSource {

    suspend fun loginUser(loginUser: LoginUser) :NetworkResult<LoginResponse>

    suspend fun signUpUser(signUpUser: SignUpUser):NetworkResult<SignUpResponse>
}



class RemoteAuthDataSourceImpl @Inject constructor(
    private val api: CartifyApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
):RemoteAuthDataSource{

    override suspend fun loginUser(loginUser: LoginUser): NetworkResult<LoginResponse> {
        return withContext(ioDispatcher){
            safeApiCall { api.loginUser(loginUser = loginUser) }
        }
    }

    override suspend fun signUpUser(signUpUser: SignUpUser): NetworkResult<SignUpResponse> {
        return withContext(ioDispatcher){
            safeApiCall { api.signUpUser(signUpUser = signUpUser) }
        }
    }

}