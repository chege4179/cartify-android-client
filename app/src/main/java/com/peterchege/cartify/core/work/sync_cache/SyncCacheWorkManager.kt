package com.peterchege.cartify.core.work.sync_cache

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.peterchege.cartify.core.work.WorkConstants
import com.peterchege.cartify.core.work.anyRunning
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import javax.inject.Inject

interface SyncCacheWorkManager {

    val isSyncing : Flow<Boolean>

    suspend fun startSyncing()
}


class SyncCacheWorkManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
):SyncCacheWorkManager{

    override val isSyncing: Flow<Boolean> =
        WorkManager.getInstance(context).getWorkInfosForUniqueWorkLiveData(
        WorkConstants.syncProductsWorkerName
    )
        .map(MutableList<WorkInfo>::anyRunning)
        .asFlow()
        .conflate()


    override suspend fun startSyncing() {
        TODO("Not yet implemented")
    }



}