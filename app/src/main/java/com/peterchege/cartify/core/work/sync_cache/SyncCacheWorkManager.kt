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
package com.peterchege.cartify.core.work.sync_cache

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.peterchege.cartify.core.work.DelegatingWorker
import com.peterchege.cartify.core.work.WORKER_CLASS_NAME
import com.peterchege.cartify.core.work.delegatedData
import com.peterchege.cartify.core.work.WorkConstants
import com.peterchege.cartify.core.work.anyRunning
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import javax.inject.Inject

interface SyncCacheWorkManager {

    val isSyncing: Flow<Boolean>

    suspend fun startSyncing()
}


class SyncCacheWorkManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : SyncCacheWorkManager {

    override val isSyncing: Flow<Boolean> =
        WorkManager.getInstance(context).getWorkInfosForUniqueWorkLiveData(
            WorkConstants.syncProductsWorkerName
        )
            .map(List<WorkInfo>::anyRunning)
            .asFlow()
            .conflate()


    override suspend fun startSyncing() {
        val addProductRequest =
            OneTimeWorkRequestBuilder<DelegatingWorker>()
                .setInputData(
                    Data.Builder()
                        .putString(WORKER_CLASS_NAME, SyncCacheWorker::class.qualifiedName)
                        .build()
                )
                .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build()
        val workManager = WorkManager.getInstance(context)
        workManager.beginUniqueWork(
            WorkConstants.syncProductsWorkerName,
            ExistingWorkPolicy.KEEP,
            addProductRequest,
        )
            .enqueue()
    }


}