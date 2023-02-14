package com.peterchege.cartify.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.peterchege.cartify.core.util.Constants
import com.peterchege.cartify.core.api.CartifyApi
import com.peterchege.cartify.core.datastore.repository.UserDataStoreRepository
import com.peterchege.cartify.core.room.database.CartifyDatabase
import com.peterchege.cartify.data.CartRepositoryImpl
import com.peterchege.cartify.data.OrderRepositoryImpl
import com.peterchege.cartify.data.ProductRepositoryImpl
import com.peterchege.cartify.data.UserRepositoryImpl
import com.peterchege.cartify.domain.repository.CartRepository
import com.peterchege.cartify.domain.repository.OrderRepository
import com.peterchege.cartify.domain.repository.ProductRepository
import com.peterchege.cartify.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCartifyApi(): CartifyApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
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
    fun provideUserDataStore(@ApplicationContext context: Context):UserDataStoreRepository {
        return UserDataStoreRepository(context = context)
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

