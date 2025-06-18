package com.loaizasoftware.usershandlerapp.data.repositories_impl

import com.loaizasoftware.usershandlerapp.data.database.MyDatabaseHelper
import com.loaizasoftware.usershandlerapp.domain.model.User
import com.loaizasoftware.usershandlerapp.domain.repositories.UserRepository

class UserRepositoryImpl(private val dbHelper: MyDatabaseHelper): UserRepository {

    override fun addUser(user: User): Boolean {
        return dbHelper.insertUser(user.name, user.age)
    }

    override fun getAllUsers(): List<User> {
        return dbHelper.getAllUsers()
    }

    override fun deleteUserById(userId: Int): Boolean {
        return dbHelper.deleteUserById(userId)
    }

}