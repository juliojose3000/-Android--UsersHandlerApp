package com.loaizasoftware.usershandlerapp.utils.workers

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.firestore
import com.loaizasoftware.usershandlerapp.data.local.database.AppDatabase
import com.loaizasoftware.usershandlerapp.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Utility object that provides methods for managing synchronization between
 * a local Room database and a remote Firebase Firestore collection.
 *
 * It includes:
 * - Triggering an immediate one-time sync using WorkManager.
 * - Listening for real-time updates from Firestore to keep local data in sync.
 */
object SyncUtils {

    /**
     * Triggers a one-time synchronization by enqueuing a [SyncWorker] via [WorkManager].
     *
     * This is useful when you want to sync data immediately after a user-related operation
     * (e.g., add, update, or delete) rather than waiting for the periodic worker to run.
     *
     * @param context The application context used to access WorkManager.
     *
     * Constraints:
     * - The device must be connected to a network for the worker to execute.
     */
    fun triggerImmediateSync(context: Context) {
        val request = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueue(request)
    }

    /**
     * Sets up a Firestore real-time listener on the "users" collection.
     *
     * When changes are detected in Firestore (added/updated documents),
     * this method:
     * - Deserializes the documents to [User] objects.
     * - Filters out users that already exist in the local database using [UserDao.existsUser].
     * - Inserts the new users into the Room database via [UserDao.insertUser].
     *
     * Note: This listener runs on the IO dispatcher to avoid blocking the main thread.
     *
     * @param context The application context used to access the Room database.
     */
    fun setupFirestoreRealTimeUpdates(context: Context) {

        Firebase.firestore.collection("users")
            .addSnapshotListener { snapshots, error ->
                if (error != null || snapshots == null) {
                    Log.e("Firestore", "Snapshot listener error", error)
                    return@addSnapshotListener
                }

                val db = AppDatabase.getDatabase(context = context)
                val userDao = db.userDao()

                CoroutineScope(Dispatchers.IO).launch {

                    for(change in snapshots.documentChanges) {

                        val user = change.document.toObject(User::class.java)

                        when(change.type) {

                            DocumentChange.Type.ADDED -> {
                                //If the user id doesn't exist, add the user
                                if (!userDao.existsUser(user.id!!)) {
                                    userDao.insertUser(user)
                                }
                            }
                            DocumentChange.Type.MODIFIED -> {
                                userDao.insertUser(user) // Insert will replace due to @PrimaryKey
                            }
                            DocumentChange.Type.REMOVED -> {
                                if (user.id != null) {
                                    userDao.deleteUser(user)
                                }
                            }

                        }

                    }

                }

            }

    }
}
