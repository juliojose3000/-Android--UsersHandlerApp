package com.loaizasoftware.usershandlerapp

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.loaizasoftware.usershandlerapp.utils.workers.SyncWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp //Initializes hilt
class UsersHandlerApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initWorker()
    }

    /**
     * Initializes and schedules a [SyncWorker] using [WorkManager] to run periodically.
     *
     * This method ensures that local unsynced data is synchronized with the remote database
     * (e.g., Firebase Firestore) every 15 minutes, but only when the device is connected to a network.
     *
     * The task is scheduled as a unique periodic work to prevent duplicate workers from being enqueued.
     * If a worker with the same name ("SyncWorker") already exists, it will be kept and not replaced.
     *
     * This method is typically called in the [Application.onCreate] method to set up background sync
     * when the app starts.
     *
     * Constraints:
     * - Minimum interval: 15 minutes (enforced by Android)
     * - Requires network connectivity
     *
     * WorkManager Policy:
     * - [ExistingPeriodicWorkPolicy.KEEP]: keeps existing scheduled work and avoids duplication.
     */
    private fun initWorker() {

        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES) // ✅ Must be at least 15 minutes
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "SyncWorker",
            ExistingPeriodicWorkPolicy.KEEP, // Avoids scheduling duplicate workers
            syncRequest
        )

    }

}