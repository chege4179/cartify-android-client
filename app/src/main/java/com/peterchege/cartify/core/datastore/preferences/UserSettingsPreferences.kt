package com.peterchege.cartify.core.datastore.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.peterchege.cartify.core.util.Constants
import com.peterchege.cartify.core.util.Constants.THEME_OPTIONS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class UserSettingsPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun setTheme(themeValue: String) {
        dataStore.edit { preferences ->
            preferences[THEME_OPTIONS] = themeValue
        }
    }
    fun getTheme(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[THEME_OPTIONS] ?: Constants.LIGHT_MODE
        }
    }

}