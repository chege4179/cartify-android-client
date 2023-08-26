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