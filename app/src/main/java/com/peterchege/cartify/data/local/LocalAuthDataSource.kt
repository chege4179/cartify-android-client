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