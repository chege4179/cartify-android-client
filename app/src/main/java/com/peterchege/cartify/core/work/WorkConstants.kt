package com.peterchege.cartify.core.work

import androidx.work.WorkInfo

object WorkConstants {
    const val NOTIFICATION_CHANNEL = "notification_channel"

    const val syncProductsWorkerName = "sync_products"
}

val List<WorkInfo>.anyRunning get() = any { it.state == WorkInfo.State.RUNNING }
