package com.loaizasoftware.usershandlerapp.data.repositories_impl

import com.loaizasoftware.usershandlerapp.data.local.dao.UserDao
import com.loaizasoftware.usershandlerapp.data.local.database.MyDatabaseHelper
import com.loaizasoftware.usershandlerapp.domain.model.User
import com.loaizasoftware.usershandlerapp.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    //private val dbHelper: MyDatabaseHelper
    private val userDao: UserDao
): UserRepository {

    override suspend fun addUser(user: User) {
        //return dbHelper.insertUser(user.name, user.age)
        userDao.insertUser(user)
    }

    override suspend fun getAllUsers(): Flow<List<User>> {
        //return dbHelper.getAllUsers()
        return userDao.getAllUsers()
    }

    override suspend fun deleteUser(user: User) {
        //return dbHelper.deleteUserById(userId)
        return userDao.deleteUser(user)
    }

    override suspend fun getAllUnsyncedUsers(): Flow<List<User>> {
        return userDao.getUnsyncedUsers()
    }

}