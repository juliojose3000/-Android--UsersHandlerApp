package com.loaizasoftware.usershandlerapp.utils.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.loaizasoftware.usershandlerapp.data.local.database.AppDatabase
import com.loaizasoftware.usershandlerapp.data.repositories_impl.UserRepositoryImpl
import com.loaizasoftware.usershandlerapp.domain.usecase.SyncDataUseCase


/**
 * A [CoroutineWorker] implementation responsible for synchronizing local user data
 * with a remote Firestore database when certain conditions are met (e.g., network available).
 *
 * This worker initializes the required database and repository components manually (non-Hilt setup)
 * and delegates the sync logic to [SyncDataUseCase].
 *
 * It is typically scheduled with [WorkManager] and can be used to implement an offline-first strategy
 * where local changes are pushed to the remote backend once connectivity is restored.
 *
 * @param context The application context.
 * @param params Parameters used to configure the worker instance.
 */

class SyncWorker(
    val context: Context,
    params: WorkerParameters
): CoroutineWorker(context, params) {

    private lateinit var syncDataUseCase: SyncDataUseCase


    /**
     * Executes the background synchronization task.
     *
     * Initializes the Room database and repository, then runs [SyncDataUseCase] to push local
     * unsynced user data to Firestore.
     *
     * @return [Result.success] if the sync operation completes without exception,
     *         [Result.retry] if an error occurs to allow retrying the task later.
     */

    override suspend fun doWork(): Result {
        return try {

            val db = AppDatabase.getDatabase(context = context)
            val userDao = db.userDao()
            val userRepository = UserRepositoryImpl(userDao)
            syncDataUseCase = SyncDataUseCase(userRepository)

            syncDataUseCase.run(Unit) // Call the use case

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

}