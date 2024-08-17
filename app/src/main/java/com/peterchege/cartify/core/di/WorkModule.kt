package com.peterchege.cartify.core.di

import android.content.Context
import com.peterchege.cartify.core.work.sync_cache.SyncCacheWorkManager
import com.peterchege.cartify.core.work.sync_cache.SyncCacheWorkManagerImpl
import com.peterchege.cartify.core.work.sync_cache.SyncCacheWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WorkModule {

    @Provides
    @Singleton
    fun provideSyncCacheWorker(
        @ApplicationContext context: Context
    ):SyncCacheWorkManager{
        return SyncCacheWorkManagerImpl(context = context)
    }
}