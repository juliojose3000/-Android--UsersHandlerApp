package com.loaizasoftware.usershandlerapp.domain.repositories

import com.loaizasoftware.usershandlerapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun addUser(user: User)

    suspend fun getAllUsers(): Flow<List<User>>

    suspend fun deleteUser(user: User)

    suspend fun getAllUnsyncedUsers(): Flow<List<User>>

}