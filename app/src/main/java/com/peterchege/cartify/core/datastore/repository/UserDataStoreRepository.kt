package com.peterchege.cartify.core.datastore.repository

import android.content.Context
import androidx.datastore.dataStore
import com.peterchege.cartify.core.datastore.serializers.UserInfoSerializer
import com.peterchege.cartify.domain.models.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

val Context.userDataStore by dataStore("user.json", UserInfoSerializer)


class UserDataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
){

    fun getLoggedInUser(): Flow<User?> {
        return context.userDataStore.data
    }
    suspend fun setLoggedInUser(user: User) {
        context.userDataStore.updateData {
            user
        }
    }
    suspend fun unsetLoggedInUser() {
        context.userDataStore.updateData {
            null
        }
    }

}