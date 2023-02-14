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
package com.peterchege.cartify.core.util

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val RemoteURL = "https://cartifyserver.vercel.app"
    const val LocalURL = "http://10.0.2.2:7000"
    const val BASE_URL = RemoteURL
    const val DATABASE_NAME ="cartify_db"

    const val CHANNEL_ID = "CHANNEL_ID"

    val THEME_OPTIONS = stringPreferencesKey("theme_options")
    const val USER_PREFERENCES = "USER_PREFERENCES"

    const val FCM_TOKEN ="FCM_TOKEN"

    const val LIGHT_MODE= "LIGHT_MODE"
    const val DARK_MODE = "DARK_MODE"

}