package com.peterchege.cartify.data

import android.content.SharedPreferences
import com.peterchege.cartify.core.datastore.repository.UserDataStoreRepository
import com.peterchege.cartify.domain.models.User
import com.peterchege.cartify.core.util.Constants
import com.peterchege.cartify.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository,
):UserRepository {
    override fun getCurrentUser(): Flow<User?> {
        return userDataStoreRepository.getLoggedInUser()
    }
    override suspend fun saveUser(user: User){
        return userDataStoreRepository.setLoggedInUser(user = user)
    }
    override suspend fun logoutUser(){
        return userDataStoreRepository.unsetLoggedInUser()
    }
}