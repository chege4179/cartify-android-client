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
package com.peterchege.cartify.data

import com.peterchege.cartify.core.datastore.preferences.DefaultSettingsProvider
import com.peterchege.cartify.core.di.IoDispatcher
import com.peterchege.cartify.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val defaultSettingsProvider: DefaultSettingsProvider,
    @IoDispatcher private val ioDispatcher:CoroutineDispatcher

):SettingsRepository {
    override fun getTheme(): Flow<String> {
        return defaultSettingsProvider.getTheme().flowOn(ioDispatcher)
    }

    override suspend fun setTheme(themeValue: String) {
        withContext(ioDispatcher){
            defaultSettingsProvider.setTheme(themeValue = themeValue)
        }
    }
}