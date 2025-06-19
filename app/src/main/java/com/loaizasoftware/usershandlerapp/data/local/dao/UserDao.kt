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

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Define conflict strategy
    suspend fun insertUser(user: User): Long // Returns new rowId

    @Delete
    suspend fun deleteUser(user: User)

}