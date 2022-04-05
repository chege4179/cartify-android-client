package com.peterchege.cartify.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.peterchege.cartify.CartifyApp
import com.peterchege.cartify.util.Constants
import com.peterchege.cartify.api.CartifyApi
import com.peterchege.cartify.room.database.CartifyDatabase
import com.peterchege.cartify.room.entities.cartItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCartifyApi():CartifyApi{
        var okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(CartifyApi::class.java)
    }
    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): SharedPreferences {
        return app.getSharedPreferences("user", Context.MODE_PRIVATE)
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
}

