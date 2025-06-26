package com.loaizasoftware.usershandlerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.loaizasoftware.usershandlerapp.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    /**
     * Retrieves all users from the local database **excluding** those marked for deletion.
     *
     * Users with `syncAction = 2` ([SyncActionEnum.DELETE]) are excluded because they are
     * intended to be removed both locally and remotely.
     *
     * @return A [Flow] emitting a list of users whose `syncAction` is not DELETE,
     *         allowing UI layers to observe real-time updates.
     */
    @Query("SELECT * FROM users WHERE syncAction != 2")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE isSynced = 0")
    fun getUnsyncedUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Define conflict strategy
    suspend fun insertUser(user: User): Long // Returns new rowId

    @Delete
    suspend fun deleteUser(user: User)

    @Query("UPDATE users SET isSynced = 1 WHERE id = :id")
    fun markUserAsSynced(id: Int)

    /**
     * Marks a user for deletion by updating their `syncAction` to [SyncActionEnum.DELETE] (value = 2)
     * and setting `isSynced` to 0 (false), indicating that this change needs to be synchronized
     * with the remote database.
     *
     * This allows the app to use an "offline-first" approach, deleting the user from the backend
     * once internet connectivity is available.
     *
     * @param id The ID of the user to mark for deletion.
     */
    @Query("UPDATE users SET isSynced = 0, syncAction = 2 WHERE id = :id")
    suspend fun setUserToDelete(id: Int)

}