package com.peterchege.cartify.domain.repository

import com.peterchege.cartify.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getCurrentUser(): Flow<User?>

    suspend fun saveUser(user:User)

    suspend fun logoutUser()
}