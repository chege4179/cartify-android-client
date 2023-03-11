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
package com.peterchege.cartify.core.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.peterchege.cartify.core.util.Constants
import com.peterchege.cartify.core.api.CartifyApi
import com.peterchege.cartify.core.datastore.preferences.UserSettingsPreferences
import com.peterchege.cartify.core.datastore.repository.UserDataStoreRepository
import com.peterchege.cartify.core.room.database.CartifyDatabase
import com.peterchege.cartify.data.*
import com.peterchege.cartify.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(
        @ApplicationContext context: Context,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                ChuckerInterceptor.Builder(context = context)
                    .collector(ChuckerCollector(context = context))
                    .maxContentLength(length = 250000L)
                    .redactHeaders(headerNames = emptySet())
                    .alwaysReadResponseBody(enable = false)
                    .build()
            )
            .build()
    }
    @Provides
    @Singleton
    fun provideCartifyApi(client: OkHttpClient): CartifyApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(CartifyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCartifyDatabase(app: Application): CartifyDatabase {
        return Room.databaseBuilder(
            app,
            CartifyDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }
    @Provides
    @Singleton
    fun provideDatastorePreferences(@ApplicationContext context: Context):
            DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(Constants.USER_PREFERENCES)
            }
        )
    @Provides
    @Singleton
    fun provideUserSettingPreferences(dataStore: DataStore<Preferences>): UserSettingsPreferences {
        return UserSettingsPreferences(dataStore = dataStore)
    }
    @Provides
    @Singleton
    fun provideUserDataStore(@ApplicationContext context: Context):UserDataStoreRepository {
        return UserDataStoreRepository(context = context)
    }
    @Provides
    @Singleton
    fun providesSettingsRepository(userSettingsPreferences: UserSettingsPreferences):SettingsRepository{
        return SettingsRepositoryImpl(
            userSettingsRepository = userSettingsPreferences
        )
    }

    @Provides
    @Singleton
    fun providesProductRepository(api:CartifyApi,db:CartifyDatabase):ProductRepository{
        return ProductRepositoryImpl(
            api = api,
            db = db
        )
    }
    @Provides
    @Singleton
    fun providesCartRepository(db:CartifyDatabase):CartRepository{
        return CartRepositoryImpl(
            db = db
        )
    }

    @Provides
    @Singleton
    fun providesOrderRepository(db:CartifyDatabase,api:CartifyApi): OrderRepository {
        return OrderRepositoryImpl(
            db = db,
            api = api,

        )
    }
    @Provides
    @Singleton
    fun providesUserRepository(dataStoreRepository: UserDataStoreRepository):UserRepository{
        return UserRepositoryImpl(
            userDataStoreRepository = dataStoreRepository
        )
    }
}

