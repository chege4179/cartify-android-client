package com.peterchege.cartify.core.work.sync_cache

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.peterchege.cartify.R
import com.peterchege.cartify.core.api.NetworkResult
import com.peterchege.cartify.core.work.WorkConstants
import com.peterchege.cartify.data.local.cached_products.CachedProductsDataSource
import com.peterchege.cartify.data.remote.RemoteProductsDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlin.random.Random


@HiltWorker
class SyncCacheWorker @AssistedInject constructor(
    @Assisted private val appContext:Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val remoteProductsDataSource: RemoteProductsDataSource,
    private val cachedProductsDataSource: CachedProductsDataSource,
): CoroutineWorker(appContext,workerParameters){

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            Random.nextInt(),
            NotificationCompat.Builder(appContext, WorkConstants.NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Syncing")
                .build()

        )
    }
    override suspend fun doWork(): Result {
        val remoteProducts = remoteProductsDataSource.getAllProducts()
        when(remoteProducts){
            is NetworkResult.Success -> {
                cachedProductsDataSource.deleteAllCachedProducts()
                cachedProductsDataSource.insertCachedProducts(products = remoteProducts.data.products)
                return Result.success()
            }
            is NetworkResult.Error -> {

                Result.retry()
                return Result.failure()
            }
            is NetworkResult.Exception -> {
                Result.retry()
                return Result.failure()
            }
        }
    }
}