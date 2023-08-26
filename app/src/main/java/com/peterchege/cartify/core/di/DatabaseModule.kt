package com.peterchege.cartify.core.di

import android.app.Application
import androidx.room.Room
import com.peterchege.cartify.core.room.database.CartifyDatabase
import com.peterchege.cartify.core.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideCartifyDatabase(app: Application): CartifyDatabase {
        return Room.databaseBuilder(
            app,
            CartifyDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }
}