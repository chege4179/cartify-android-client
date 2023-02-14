package com.peterchege.cartify.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {


    fun getTheme(): Flow<String>


    suspend fun setTheme(themeValue:String)
}