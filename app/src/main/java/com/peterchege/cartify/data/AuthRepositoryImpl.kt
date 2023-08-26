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
package com.peterchege.cartify.data

import com.peterchege.cartify.core.api.NetworkResult
import com.peterchege.cartify.core.api.requests.LoginUser
import com.peterchege.cartify.core.api.requests.SignUpUser
import com.peterchege.cartify.core.api.responses.LoginResponse
import com.peterchege.cartify.core.api.responses.SignUpResponse
import com.peterchege.cartify.data.local.LocalAuthDataSource
import com.peterchege.cartify.data.remote.RemoteAuthDataSource
import com.peterchege.cartify.domain.models.User
import com.peterchege.cartify.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val localAuthDataSource:LocalAuthDataSource,
    private val remoteAuthDataSource: RemoteAuthDataSource,
):AuthRepository {

    override suspend fun loginUser(loginUser: LoginUser): NetworkResult<LoginResponse> {
        return remoteAuthDataSource.loginUser(loginUser = loginUser)
    }

    override suspend fun signUpUser(signUpUser: SignUpUser): NetworkResult<SignUpResponse> {
        return remoteAuthDataSource.signUpUser(signUpUser)
    }
    override fun getCurrentUser(): Flow<User?> {
        return localAuthDataSource.getCurrentUser()
    }
    override suspend fun saveUser(user: User){
        return localAuthDataSource.saveUser(user)
    }
    override suspend fun logoutUser(){
        return localAuthDataSource.logoutUser()
    }
}