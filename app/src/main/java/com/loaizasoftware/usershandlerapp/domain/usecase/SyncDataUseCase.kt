package com.loaizasoftware.usershandlerapp.domain.usecase

import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.firestore.firestore
import com.loaizasoftware.usershandlerapp.domain.model.SyncActionEnum
import com.loaizasoftware.usershandlerapp.domain.model.User
import com.loaizasoftware.usershandlerapp.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Use case responsible for synchronizing local user data with a remote Firestore database.
 *
 * This use case iterates over all users marked as unsynced in the local database and performs
 * the corresponding remote operation based on the [SyncActionEnum] flag:
 *
 * - [SyncActionEnum.ADD] or [SyncActionEnum.UPDATE]: Pushes the user data to Firestore using `.set()`.
 * - [SyncActionEnum.DELETE]: Removes the user document from Firestore and deletes it from the local database.
 *
 * After a successful remote operation, the user is marked as synced locally.
 *
 * @param userRepository The repository interface to access local user data and perform updates.
 */

class SyncDataUseCase @Inject constructor(private val userRepository: UserRepository) :
    UseCase<Unit, Flow<List<User>>>() {


    /**
     * Executes the synchronization process by collecting all unsynced users from the local database
     * and applying the appropriate operation in the Firestore database.
     *
     * @param params Unused input parameter for this use case.
     * @return A [Flow] containing the updated list of users that are still marked as unsynced
     *         (e.g., due to failed operations).
     */

    override suspend fun run(params: Unit): Flow<List<User>> {

        userRepository.getAllUnsyncedUsers().collect { unsyncedUsers ->

            unsyncedUsers.forEach { user ->

                try {

                    val docRef = Firebase.firestore.collection("users").document(user.id.toString())

                    when (user.syncAction) {
                        SyncActionEnum.ADD.value, SyncActionEnum.UPDATE.value -> {
                            docRef.set(user).await()
                            userRepository.markUserAsSynced(user.id!!)
                        }

                        SyncActionEnum.DELETE.value -> {
                            docRef.delete().await()
                            userRepository.deleteUser(user)
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        }

        return userRepository.getAllUnsyncedUsers()

    }

}