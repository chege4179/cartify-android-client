package com.peterchege.cartify.data

import com.peterchege.cartify.core.datastore.preferences.UserSettingsPreferences
import com.peterchege.cartify.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val userSettingsRepository: UserSettingsPreferences

):SettingsRepository {
    override fun getTheme(): Flow<String> {
        return userSettingsRepository.getTheme()
    }

    override suspend fun setTheme(themeValue: String) {
        return userSettingsRepository.setTheme(themeValue = themeValue)
    }
}