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
package com.peterchege.cartify.data.local

import com.peterchege.cartify.core.datastore.repository.DefaultAuthProvider
import com.peterchege.cartify.core.di.IoDispatcher
import com.peterchege.cartify.domain.models.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface LocalAuthDataSource {

    fun getCurrentUser(): Flow<User?>

    suspend fun saveUser(user: User)

    suspend fun logoutUser()
}

class LocalAuthDataSourceImpl @Inject constructor(
    private val defaultAuthProvider: DefaultAuthProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
):LocalAuthDataSource{
    override fun getCurrentUser(): Flow<User?> {
        return defaultAuthProvider.getLoggedInUser().flowOn(ioDispatcher)
    }

    override suspend fun saveUser(user: User) {
        withContext(ioDispatcher){
            defaultAuthProvider.setLoggedInUser(user = user)
        }
    }

    override suspend fun logoutUser() {
        withContext(ioDispatcher){
            defaultAuthProvider.unsetLoggedInUser()
        }
    }


}