package com.peterchege.cartify.core.di

import com.peterchege.cartify.core.api.CartifyApi
import com.peterchege.cartify.core.datastore.preferences.DefaultSettingsProvider
import com.peterchege.cartify.core.datastore.repository.DefaultAuthProvider
import com.peterchege.cartify.core.room.database.CartifyDatabase
import com.peterchege.cartify.data.AuthRepositoryImpl
import com.peterchege.cartify.data.CartRepositoryImpl
import com.peterchege.cartify.data.OrderRepositoryImpl
import com.peterchege.cartify.data.ProductRepositoryImpl
import com.peterchege.cartify.data.SettingsRepositoryImpl
import com.peterchege.cartify.data.local.LocalAuthDataSource
import com.peterchege.cartify.data.local.LocalAuthDataSourceImpl
import com.peterchege.cartify.data.local.cached_products.CachedProductDataSourceImpl
import com.peterchege.cartify.data.local.cached_products.CachedProductsDataSource
import com.peterchege.cartify.data.local.saved_products.SavedProductsDataSource
import com.peterchege.cartify.data.local.saved_products.SavedProductsDataSourceImpl
import com.peterchege.cartify.data.remote.RemoteAuthDataSource
import com.peterchege.cartify.data.remote.RemoteAuthDataSourceImpl
import com.peterchege.cartify.data.remote.RemoteProductsDataSource
import com.peterchege.cartify.data.remote.RemoteProductsDataSourceImpl
import com.peterchege.cartify.domain.repository.AuthRepository
import com.peterchege.cartify.domain.repository.CartRepository
import com.peterchege.cartify.domain.repository.OrderRepository
import com.peterchege.cartify.domain.repository.ProductRepository
import com.peterchege.cartify.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesSettingsRepository(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        defaultSettingsProvider: DefaultSettingsProvider,
    ): SettingsRepository {
        return SettingsRepositoryImpl(
            defaultSettingsProvider = defaultSettingsProvider,
            ioDispatcher = ioDispatcher,
        )
    }

    @Provides
    @Singleton
    fun providesSavedProductRepository(
        db: CartifyDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): SavedProductsDataSource {
        return SavedProductsDataSourceImpl(db = db, ioDispatcher = ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesCachedProductRepository(
        db: CartifyDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): CachedProductsDataSource {
        return CachedProductDataSourceImpl(db = db, ioDispatcher = ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesRemoteProductRepository(
        api: CartifyApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): RemoteProductsDataSource {
        return RemoteProductsDataSourceImpl(api = api, ioDispatcher = ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesProductRepository(
        cachedProductsDataSource: CachedProductsDataSource,
        savedProductsDataSource: SavedProductsDataSource,
        remoteProductsDataSource: RemoteProductsDataSource,
    ): ProductRepository {
        return ProductRepositoryImpl(
            cachedProductsDataSource = cachedProductsDataSource,
            savedProductsDataSource = savedProductsDataSource,
            remoteProductsDataSource = remoteProductsDataSource,
        )
    }

    @Provides
    @Singleton
    fun providesCartRepository(db: CartifyDatabase): CartRepository {
        return CartRepositoryImpl(
            db = db
        )
    }

    @Provides
    @Singleton
    fun providesOrderRepository(
        api: CartifyApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): OrderRepository {
        return OrderRepositoryImpl(
            ioDispatcher = ioDispatcher,
            api = api,
        )
    }

    @Provides
    @Singleton
    fun providesLocalAuthDataSource(
        defaultAuthProvider: DefaultAuthProvider,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ):LocalAuthDataSource{
        return LocalAuthDataSourceImpl(
            defaultAuthProvider = defaultAuthProvider,
            ioDispatcher = ioDispatcher
        )

    }

    @Provides
    @Singleton
    fun providesRemoteAuthDataSource(
        api :CartifyApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ):RemoteAuthDataSource{
        return RemoteAuthDataSourceImpl(
            api = api,
            ioDispatcher = ioDispatcher
        )

    }




    @Provides
    @Singleton
    fun providesAuthRepository(
        localAuthDataSource: LocalAuthDataSource,
        remoteAuthDataSource: RemoteAuthDataSource,
    ): AuthRepository {
        return AuthRepositoryImpl(
            localAuthDataSource = localAuthDataSource,
            remoteAuthDataSource = remoteAuthDataSource
        )
    }
}