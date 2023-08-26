package com.peterchege.cartify.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.peterchege.cartify.core.datastore.preferences.DefaultSettingsProvider
import com.peterchege.cartify.core.datastore.repository.DefaultAuthProvider
import com.peterchege.cartify.core.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

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
    fun provideUserSettingPreferences(dataStore: DataStore<Preferences>): DefaultSettingsProvider {
        return DefaultSettingsProvider(dataStore = dataStore)
    }
    @Provides
    @Singleton
    fun provideUserDataStore(@ApplicationContext context: Context): DefaultAuthProvider {
        return DefaultAuthProvider(context = context)
    }
}