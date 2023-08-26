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
package com.peterchege.cartify.domain.repository

import com.peterchege.cartify.core.api.NetworkResult
import com.peterchege.cartify.core.api.requests.LoginUser
import com.peterchege.cartify.core.api.requests.SignUpUser
import com.peterchege.cartify.core.api.responses.LoginResponse
import com.peterchege.cartify.core.api.responses.SignUpResponse
import com.peterchege.cartify.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun loginUser(loginUser: LoginUser): NetworkResult<LoginResponse>
    suspend fun signUpUser(signUpUser: SignUpUser):NetworkResult<SignUpResponse>

    fun getCurrentUser(): Flow<User?>

    suspend fun saveUser(user:User)

    suspend fun logoutUser()
}